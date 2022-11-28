#%%
import uvicorn
from fastapi import FastAPI
import pandas as pd
import numpy as np
import tensorflow as tf
from tensorflow import keras
import pickle
from CV_note import CV_note
import re
from nltk.tokenize import word_tokenize
from nltk.corpus import stopwords
from tensorflow.keras.preprocessing.sequence import pad_sequences

#model = keras.models.load_model('models_P')
#%%
app = FastAPI()
# Load model trained
filterCV=keras.models.load_model('models_P')
# create global parameter
stopwords_set = set(stopwords.words('english')+['``',"''"])
max_length = 300
trunc_type = 'post'
pad_type = 'post'

@app.post('/predict')
def predict_cv(data: str):
    # clean data before convert to number
    resume_data = clean_text(data)
    # load feature_tokenizer to transfer data to number array
    feature_tokenizer_in = open("feature_tokenizer.pickle","rb")
    feature_tokenizer = pickle.load(feature_tokenizer_in)

    resume_sequence = feature_tokenizer.texts_to_sequences([resume_data])
    # padding 0 for number array until reach max_length length
    resume_padded = pad_sequences(resume_sequence, maxlen=max_length, padding=pad_type, truncating=trunc_type)
    # convert to numpy array
    resume_padded = np.array(resume_padded)

    #predict
    prediction = filterCV.predict([resume_padded])

    #Get top 5 highest %
    indices = np.argpartition(prediction[0], -5)[-5:]
    indices = indices[np.argsort(prediction[0][indices])]
    indices = list(reversed(indices))

    #Load the lable
    encoding_to_label_in = open("dictionary.pickle","rb")
    encoding_to_label = pickle.load(encoding_to_label_in)
    
    # Concat data to return
    result_data = ""
    for index in indices:
        result_data += str(round(prediction[0][index]*100,2)) + "% " + str(encoding_to_label[index]) + " - "
    return result_data
@app.get('/home')
def get_home():
    return {'message': 'Wellcome'}

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
if __name__ == '__main__':
    uvicorn.run(app, host='127.0.0.1', port=8000)
