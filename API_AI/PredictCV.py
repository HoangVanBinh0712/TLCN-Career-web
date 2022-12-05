#%% Import libraries
import matplotlib.pyplot as plt
import nltk
from nltk.corpus import stopwords
from nltk.tokenize import word_tokenize
import numpy as np
import pandas as pd
import pickle
import re
from sklearn import metrics
from sklearn.metrics import accuracy_score
from sklearn.utils import shuffle
import string
import tensorflow as tf
from tensorflow.keras.preprocessing.sequence import pad_sequences
from tensorflow.keras.preprocessing.text import Tokenizer
import warnings
import seaborn as sns
warnings.filterwarnings('ignore')
np.set_printoptions(precision=4)

nltk.download('stopwords')
nltk.download('punkt')
#%% Load data
data = pd.read_csv('dataset\ResumeDataSet.csv', engine='python')
data.head()
#%%Bar graph visualization
plt.figure(figsize=(15,15))
plt.xticks(rotation=90)
sns.countplot(y="Category", data=data)
# %% Get set of stopwords
#stopwords_set = set(stopwords.words('english')+['``',"''"])

gist_file = open("stopword.txt", "r")
try:
    content = gist_file.read()
    stopwords_set = content.split(",")
finally:
    gist_file.close()
stopwords_set = set(stopwords_set)

# %% Function to clean resume text
def clean_text(resume_text):
    resume_text = re.sub('http\S+\s*', ' ', resume_text) 
    resume_text = re.sub('RT|cc', ' ', resume_text) 
    resume_text = re.sub('#\S+', '', resume_text) 
    resume_text = re.sub('@\S+', '  ', resume_text) 
    resume_text = re.sub('[%s]' % re.escape("""!"#$%&'()*+,-./:;<=>?@[\]^_`{|}~"""), ' ', resume_text) 
    resume_text = re.sub(r'[^\x00-\x7f]',r' ', resume_text) 
    resume_text = re.sub('\s+', ' ', resume_text) 
    resume_text = resume_text.lower()  
    resume_text_tokens = word_tokenize(resume_text) 
    filtered_text = [w for w in resume_text_tokens if not w in stopwords_set] 
    return ' '.join(filtered_text)
# %% Print a sample original resume
print('--- Original resume ---')
#print(data['Resume'][0])
data['cleaned_resume'] = data.Resume.apply(lambda x: clean_text(x))
print('--- Cleaned resume ---')
#print(data['cleaned_resume'][0])

# %%Get features and labels from data and shuffle
features = data['cleaned_resume'].values
original_labels = data['Category'].values
labels = original_labels[:]
data_size = data.__len__()
for i in range(data_size):
  labels[i] = str(labels[i].lower()) 
  labels[i] = labels[i].replace(" ", "") 
  labels[i] = labels[i].replace("-", "") 

features, labels = shuffle(features, labels)
print(features[0])
print(labels[0:10])

# %% Split for train and test
train_split = 0.8
train_size = int(train_split * data_size)

train_features = features[:train_size]
train_labels = labels[:train_size]

test_features = features[train_size:]
test_labels = labels[train_size:]

# Print size of each split
print(train_labels.__len__())
print(test_labels.__len__())

# %%Tokenize feature data and print word dictionary
vocab_size = 6000
oov_tok = '<OOV>'
feature_tokenizer = Tokenizer(num_words=vocab_size, oov_token=oov_tok)
feature_tokenizer.fit_on_texts(features)
feature_index = feature_tokenizer.word_index
#print(dict(list(feature_index.items())))
# Print example sequences from train and test datasets
train_feature_sequences = feature_tokenizer.texts_to_sequences(train_features)
test_feature_sequences = feature_tokenizer.texts_to_sequences(test_features)

# %% Tokenize label data and print label dictionary
label_tokenizer = Tokenizer(lower=True)
label_tokenizer.fit_on_texts(labels)
label_index = label_tokenizer.word_index
print(dict(list(label_index.items())))
# Print example label encodings from train and test datasets
train_label_sequences = label_tokenizer.texts_to_sequences(train_labels)
test_label_sequences = label_tokenizer.texts_to_sequences(test_labels)

# %%Pad sequences for feature data
max_length = 800
trunc_type = 'post'
pad_type = 'post'

train_feature_padded = pad_sequences(train_feature_sequences, maxlen=max_length, padding=pad_type, truncating=trunc_type)
test_feature_padded = pad_sequences(test_feature_sequences, maxlen=max_length, padding=pad_type, truncating=trunc_type)

# Print example padded sequences from train and test datasets
print(train_feature_padded[2])
print(test_feature_padded[2])

# # %% Define the neural network
# embedding_dim = 64
# units = np.unique(data['Category']).__len__() + 1
# model = tf.keras.Sequential([
#   tf.keras.layers.Embedding(vocab_size, embedding_dim, input_length=1),
#   tf.keras.layers.Bidirectional(tf.keras.layers.LSTM(embedding_dim)),
#   tf.keras.layers.Dense(embedding_dim, activation='relu'),
#   tf.keras.layers.Dense(units, activation='softmax')
# ])
# model.summary()
#%% Alternative model
embedding_dim = 64
units = np.unique(data['Category']).__len__() + 1
model = tf.keras.Sequential([
  tf.keras.layers.Embedding(input_dim=vocab_size, output_dim=embedding_dim, input_length=1),
  tf.keras.layers.GlobalMaxPooling1D(),
  tf.keras.layers.Dense(128, activation='relu'),
  tf.keras.layers.Dense(units, activation='softmax'),
])
model.summary()

#%% # Compile the model and convert train/test data into NumPy arrays
model.compile(loss='sparse_categorical_crossentropy', optimizer='adam', metrics=['accuracy'])
train_feature_padded = np.array(train_feature_padded)
test_feature_padded = np.array(test_feature_padded)
train_label_sequences = np.array(train_label_sequences)
test_label_sequences = np.array(test_label_sequences)

# %%
num_epochs = 50
history = model.fit(train_feature_padded, train_label_sequences, epochs=num_epochs, validation_data=(test_feature_padded, test_label_sequences), verbose=2)
score = model.evaluate(test_feature_padded, test_label_sequences, verbose=1)
print("Test Accuracy:", score[1])

#%% Draw accuracy model & loss model
plt.plot(history.history['accuracy'])
plt.plot(history.history['val_accuracy'])

plt.title('model accuracy')
plt.ylabel('accuracy')
plt.xlabel('epoch')
plt.legend(['train','test'], loc='upper left')
plt.show()

plt.plot(history.history['loss'])
plt.plot(history.history['val_loss'])

plt.title('model loss')
plt.ylabel('loss')
plt.xlabel('epoch')
plt.legend(['train','test'], loc='upper left')
plt.show()

# %%
model.save('models_P')

# %% Save feature tokenizer
with open('feature_tokenizer.pickle', 'wb') as handle:
    pickle.dump(feature_tokenizer, handle, protocol=pickle.HIGHEST_PROTOCOL)

# %% Save reverse dictionary of labels to encodings
label_to_encoding = dict(list(label_index.items()))
print(label_to_encoding)

encoding_to_label = {}
for k, v in label_to_encoding.items():
  encoding_to_label[v] = k
print(encoding_to_label)
with open('dictionary.pickle', 'wb') as handle:
    pickle.dump(encoding_to_label, handle, protocol=pickle.HIGHEST_PROTOCOL)


# %% Create padded sequence for example
input = "Summary Dependable construction worker with 4+ years of experience. Seeking to deliver to construction targets at Toll Brothers. Worked on 3 job sites per month at Letourneau Brothers Construction. Ensured jobs finished early and 10% under budget. Received two Employee of the Month awards for efficiency. Skilled in power tool and hand tool use. Experience Construction Worker Letourneau Brothers Construction 2015–2109 Supervised construction on 3 job sites per month, ensuring completion 10% under budget and before deadline. Used and distributed construction equipment and materials in a team of 10–20 laborers. Commended 4x by manager for work ethic. Named employee of the month 2x for efficiency and attention to detail. Assembled concrete forms for 120+ structures of 3,000+ sq ft. Assisted with construction of metal structural framework meeting or exceeding company standards in 100% of jobs. General Laborer Pierson Industries 2013–2015 Performed construction and maintenance work in a 200,000 sq ft manufacturing business. Promoted for efficiency and teamwork skills. Operated Skid Steer an average of 15 hours per month. Assisted with metal structural framework construction for 20,000 sq ft outbuilding. Helped ensure completion 3 days before target. Completed company OSHA construction training with score of 99%. Education A.A. Construction, McHenry County College 2011–2013 Excelled in power tool and equipment operation. Maintained 4.0 GPA in all OSHA Safety classes. High School Diploma, Mercer County High School, 2011 Certifications Skid Steer Certification, Caterpillar Inc. First Aid & CPR, American Red Cross Courses OSHA Education Center 15-hour Construction Safety Training Class Additional Activities Habitat for Humanity construction volunteer, 2x per month. Fixed leakage in local home with extensive French drain install. Hard Skills: power tools and hand tools, light equipment operation, OSHA safety compliance, ability to lift 100 lbs Soft Skills: time management, interpersonal skills, teamwork, communication"
resume_example = clean_text(input)
example_sequence = feature_tokenizer.texts_to_sequences([resume_example])
example_padded = pad_sequences(example_sequence, maxlen=max_length, padding=pad_type, truncating=trunc_type)
example_padded = np.array(example_padded)
#print(example_padded)
# Make a prediction
prediction = model.predict(example_padded)
# Verify that prediction has correct format
#print(prediction[0])
#print(np.sum(prediction[0])) 

# Indices of top 5 most probable solutions
indices = np.argpartition(prediction[0], -5)[-5:]
indices = indices[np.argsort(prediction[0][indices])]
indices = list(reversed(indices))
print(indices)

# Find maximum value in prediction and its index
#print(prediction[0])
print(max(prediction[0])) 
print(np.argmax(prediction[0])) 

# 
for index in indices:
    print(prediction[0][index]*100,"% ",encoding_to_label[index])

# %%
