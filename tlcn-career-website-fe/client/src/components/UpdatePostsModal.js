import Modal from "react-bootstrap/Modal";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import addImage from "../assets/add-image.png";
import { useContext, useEffect, useState } from "react";
import { EmployerPostContext } from "../contexts/EmployerPostContext";

const UpdatePostModal = () => {
  // Contexts

  const {
    updatePost,
    updatingPost,
    setUpdatingPost,
    setShowToast,
    showUpdatePost,
    setShowUpdatePost,
    fieldAndCity,
    services,
  } = useContext(EmployerPostContext);

  function monthDiff(dateTo, dateFrom) {
    dateFrom = new Date(dateFrom);
    dateTo = new Date(dateTo);
    return (
      dateTo.getMonth() -
      dateFrom.getMonth() +
      12 * (dateTo.getFullYear() - dateFrom.getFullYear())
    );
  }
  // State
  const [newPost, setNewPost] = useState({
    title: updatingPost ? updatingPost.title : "",
    description: updatingPost ? updatingPost.description : "",
    salary: updatingPost ? updatingPost.salary : "",
    salaryType: updatingPost ? updatingPost.salaryType : "",
    location: updatingPost ? updatingPost.location : "",
    recruit: updatingPost ? updatingPost.recruit : "",
    startDate: updatingPost ? updatingPost.startDate : "",
    duration: updatingPost
      ? monthDiff(updatingPost.expirationDate, updatingPost.startDate)
      : "",
    avatar: updatingPost ? updatingPost.avatar : "",
    fieldname: updatingPost ? updatingPost.field : "",
    cityname: updatingPost ? updatingPost.city : "",
    fieldCode: "",
    cityCode: "",
    service: updatingPost.service.id,
  });
  const [updateLoading, setUpdateLoading] = useState(false);

  useEffect(() => {
    setNewPost({
      id: updatingPost ? updatingPost.id : "",
      title: updatingPost ? updatingPost.title : "",
      description: updatingPost ? updatingPost.description : "",
      salary: updatingPost ? updatingPost.salary : "",
      salaryType: updatingPost ? updatingPost.salaryType : "",
      location: updatingPost ? updatingPost.location : "",
      recruit: updatingPost ? updatingPost.recruit : "",
      startDate: updatingPost ? updatingPost.startDate.split(" ")[0] : "",
      duration: updatingPost
        ? monthDiff(updatingPost.expirationDate, updatingPost.startDate)
        : "",
      avatar: updatingPost ? updatingPost.avatar : "",
      fieldname: updatingPost ? updatingPost.field : "",
      cityname: updatingPost ? updatingPost.city : "",
      fieldCode: fieldAndCity[0].filter(
        (pfield) => pfield.name === updatingPost.field
      )[0].code,
      cityCode: fieldAndCity[1].filter(
        (pcity) => pcity.name === updatingPost.city
      )[0].code,
      service: services.filter((sv) => sv.id === updatingPost.service.id)[0].id,
    });
  }, [updatingPost]);

  const {
    title,
    description,
    salary,
    salaryType,
    location,
    recruit,
    startDate,
    duration,
    avatar,
    fieldname,
    cityname,
    fieldCode,
    cityCode,
    service,
  } = newPost;

  const onChangeNewPostForm = (event) =>
    setNewPost({ ...newPost, [event.target.name]: event.target.value });

  const closeDialog = () => {
    setNewPost({ ...newPost, avatar: updatingPost?.avatar });
    setShowUpdatePost(false);
  };

  const compressImage = (imageFile, quality) => {
    return new Promise((resolve, reject) => {
      const $canvas = document.createElement("canvas");
      const image = new Image();
      image.onload = () => {
        $canvas.width = image.width;
        $canvas.height = image.height;
        $canvas.getContext("2d").drawImage(image, 0, 0);
        $canvas.toBlob(
          (blob) => {
            if (blob === null) {
              return reject(blob);
            } else {
              resolve(blob);
            }
          },
          "image/jpeg",
          quality / 100
        );
      };
      image.src = URL.createObjectURL(imageFile);
    });
  };

  const fileToBase64 = (file, cb) => {
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = function () {
      cb(null, reader.result);
    };
    reader.onerror = function (error) {
      cb(error, null);
    };
  };

  const onUploadFileChange = async ({ target }) => {
    if (target.files < 1 || !target.validity.valid) {
      return;
    }
    var preview = document.getElementById("update-img-review");

    try {
      const file = target.files[0];
      let blob = null;
      if (file) {
        blob = await compressImage(file, 50);
        preview.src = URL.createObjectURL(blob);
      }

      console.log({ blob });

      fileToBase64(blob, (err, result) => {
        if (result) {
          setNewPost({
            ...newPost,
            avatar: blob,
          });
        }
      });
    } catch (err) {
      console.log(err);
    }
  };

  const onSubmit = async (event) => {
    setUpdateLoading(true);
    event.preventDefault();
    const { success, message, data } = await updatePost(newPost, avatar);
    setShowToast({
      show: true,
      message: message,
      type: success ? "success" : "danger",
    });
    if (success) {
      setNewPost({ ...newPost, avatar: updatingPost?.avatar });
      setShowUpdatePost(false);
      setUpdatingPost(data);
    }

    setUpdateLoading(false);
  };
  let body;
  body = (
    <Form onSubmit={onSubmit}>
      <Modal.Body>
        <Row>
          <Col className="col-6">
            <Form.Group className="mb-3">
              <Form.Text id="title-help" muted>
                Title
              </Form.Text>
              <Form.Control
                type="text"
                placeholder="Title"
                name="title"
                required
                aria-describedby="title-help"
                value={title}
                onChange={onChangeNewPostForm}
              />
            </Form.Group>
            <Form.Text id="title-help" muted>
              Description
            </Form.Text>

            <Form.Group className="mb-3">
              <Form.Control
                as="textarea"
                rows={3}
                placeholder="Description"
                name="description"
                value={description}
                onChange={onChangeNewPostForm}
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Text id="title-help" muted>
                Salary
              </Form.Text>
              <Form.Control
                type="number"
                placeholder="Salary"
                name="salary"
                required
                value={salary}
                onChange={onChangeNewPostForm}
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Text id="title-help" muted>
                Salary Type
              </Form.Text>
              <Form.Control
                type="text"
                placeholder="Salary Type"
                name="salaryType"
                required
                value={salaryType}
                onChange={onChangeNewPostForm}
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Text id="title-help" muted>
                Recruit
              </Form.Text>
              <Form.Control
                type="text"
                placeholder="Recruit"
                name="recruit"
                required
                value={recruit}
                onChange={onChangeNewPostForm}
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Text id="title-help" muted>
                Start Date
              </Form.Text>
              <Form.Control
                type="Date"
                placeholder="Start Date"
                name="startDate"
                aria-describedby="title-help"
                value={startDate}
                onChange={onChangeNewPostForm}
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Text id="title-help" muted>
                Duration (Months)
              </Form.Text>
              <Form.Control
                type="Number"
                placeholder="Number of months"
                name="duration"
                required
                aria-describedby="title-help"
                value={duration}
                min={1}
                onChange={onChangeNewPostForm}
              />
            </Form.Group>
            <Form.Group>
              <Form.Label> Field</Form.Label>
              <Form.Select
                name="fieldCode"
                value={fieldCode}
                onChange={onChangeNewPostForm}
              >
                {fieldAndCity != null
                  ? fieldAndCity[0].map((pfield) => (
                      <option key={pfield.id} value={pfield.code}>
                        {pfield.name}
                      </option>
                    ))
                  : ""}
              </Form.Select>
            </Form.Group>
            <Form.Group>
              <Form.Label> City</Form.Label>
              <Form.Select
                name="cityCode"
                value={cityCode}
                onChange={onChangeNewPostForm}
              >
                {fieldAndCity != null
                  ? fieldAndCity[1].map((city) => (
                      <option key={city.id} value={city.code}>
                        {city.name}
                      </option>
                    ))
                  : ""}
              </Form.Select>
            </Form.Group>
            <Form.Group>
              <Form.Label> Service</Form.Label>
              <Form.Select
                name="service"
                value={service}
                onChange={onChangeNewPostForm}
              >
                {services.map((service) => (
                  <option key={service.id} value={service.id}>
                    {service.name}
                  </option>
                ))}
              </Form.Select>
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Text id="title-help" muted>
                Location
              </Form.Text>
              <Form.Control
                type="text"
                placeholder="Location"
                name="location"
                required
                value={location}
                onChange={onChangeNewPostForm}
              />
            </Form.Group>
          </Col>
          <Col className="col-6">
            <img
              className="img-center img-add-post"
              id="update-img-review"
              src={updatingPost.avatar}
            />
            <input
              className="center-block img-input-decorate"
              type="file"
              name="avatar"
              accept="image/*"
              onChange={onUploadFileChange}
            />
          </Col>
        </Row>
      </Modal.Body>
      <Modal.Footer>
        <Button variant="secondary" onClick={closeDialog}>
          Cancel
        </Button>
        <Button variant="primary" type="submit" disabled={updateLoading}>
          {updateLoading && (
            <span className="spinner-border spinner-border-sm mr-1"></span>
          )}
          Confirm
        </Button>
      </Modal.Footer>
    </Form>
  );

  return (
    <Modal
      className="modal-add-post"
      show={showUpdatePost}
      onHide={closeDialog}
    >
      <Modal.Header closeButton>
        <Modal.Title>Update Post</Modal.Title>
      </Modal.Header>
      {body};
    </Modal>
  );
};
export default UpdatePostModal;
