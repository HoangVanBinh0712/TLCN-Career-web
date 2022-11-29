import Modal from 'react-bootstrap/Modal'
import Button from 'react-bootstrap/Button'
import Form from 'react-bootstrap/Form'
import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col'
import { useContext, useEffect, useState } from 'react'
import { EmployerPostContext } from '../contexts/EmployerPostContext'
import axios from 'axios'
import { apiUrl } from '../contexts/constants'

const UpdatePostModal = () => {
    // Contexts

    const { updatePost, updatingPost, setUpdatingPost, setShowToast, showUpdatePost, setShowUpdatePost, fieldAndCity, services } = useContext(EmployerPostContext)
    const [updateLoading, setUpdateLoading] = useState(false)
    const [post, setPost] = useState(null)
    function monthDiff(dateTo, dateFrom) {
        dateFrom = new Date(dateFrom)
        dateTo = new Date(dateTo)
        return dateTo.getMonth() - dateFrom.getMonth() + 12 * (dateTo.getFullYear() - dateFrom.getFullYear())
    }
    // State
    const [newPost, setNewPost] = useState({
        title: post ? post.title : '',
        description: post ? post.description : '',
        salary: post ? post.salary : '',
        salaryType: post ? post.salaryType : '',
        location: post ? post.location : '',
        recruit: post ? post.recruit : '',
        startDate: post ? post.startDate : '',
        duration: post ? monthDiff(post.expirationDate, post.startDate) : '',
        avatar: post ? post.avatar : '',
        fieldname: post ? post.field : '',
        cityname: post ? post.city : '',
        fieldCode: '',
        cityCode: '',
        service: post?.service.id,
    })

    useEffect(() => {
        async function getEmployerPostDetail(id) {
            try {
                const response = await axios.get(`${apiUrl}/employer/post/${id}`)
                if (response.data.success) {
                    setPost(response.data.data)
                    setNewPost({
                        id: response.data.data ? response.data.data.id : '',
                        title: response.data.data ? response.data.data.title : '',
                        description: response.data.data ? response.data.data.description : '',
                        salary: response.data.data ? response.data.data.salary : '',
                        salaryType: response.data.data ? response.data.data.salaryType : '',
                        location: response.data.data ? response.data.data.location : '',
                        recruit: response.data.data ? response.data.data.recruit : '',
                        startDate: response.data.data ? response.data.data.startDate.split(' ')[0] : '',
                        duration: response.data.data ? monthDiff(response.data.data.expirationDate, response.data.data.startDate) : '',
                        avatar: response.data.data ? response.data.data.avatar : '',
                        fieldname: response.data.data ? response.data.data.field : '',
                        cityname: response.data.data ? response.data.data.city : '',
                        fieldCode: fieldAndCity[0].filter((pfield) => pfield.name === response.data.data.field)[0].code,
                        cityCode: fieldAndCity[1].filter((pcity) => pcity.name === response.data.data.city)[0].code,
                        service: services.filter((sv) => sv.id === response.data.data.service.id)[0].id,
                    })
                    return
                }
                setPost(null)
            } catch (error) {
                console.log(error)
                setPost(null)
            }
        }
        getEmployerPostDetail(updatingPost.id)
    }, [updatingPost])

    const { title, description, salary, salaryType, location, recruit, startDate, duration, avatar, fieldCode, cityCode, service } = newPost

    const onChangeNewPostForm = (event) => setNewPost({ ...newPost, [event.target.name]: event.target.value })

    const closeDialog = () => {
        setNewPost({ ...newPost, avatar: post?.avatar })
        setShowUpdatePost(false)
    }

    const compressImage = (imageFile, quality) => {
        return new Promise((resolve, reject) => {
            const $canvas = document.createElement('canvas')
            const image = new Image()
            image.onload = () => {
                $canvas.width = image.width
                $canvas.height = image.height
                $canvas.getContext('2d').drawImage(image, 0, 0)
                $canvas.toBlob(
                    (blob) => {
                        if (blob === null) {
                            return reject(blob)
                        } else {
                            resolve(blob)
                        }
                    },
                    'image/jpeg',
                    quality / 100
                )
            }
            image.src = URL.createObjectURL(imageFile)
        })
    }

    const fileToBase64 = (file, cb) => {
        const reader = new FileReader()
        reader.readAsDataURL(file)
        reader.onload = function () {
            cb(null, reader.result)
        }
        reader.onerror = function (error) {
            cb(error, null)
        }
    }

    const onUploadFileChange = async ({ target }) => {
        if (target.files < 1 || !target.validity.valid) {
            return
        }
        var preview = document.getElementById('update-img-review')

        try {
            const file = target.files[0]
            let blob = null
            if (file) {
                blob = await compressImage(file, 50)
                preview.src = URL.createObjectURL(blob)
            }

            console.log({ blob })

            fileToBase64(blob, (err, result) => {
                if (result) {
                    setNewPost({
                        ...newPost,
                        avatar: blob,
                    })
                }
            })
        } catch (err) {
            console.log(err)
        }
    }

    const onSubmit = async (event) => {
        setUpdateLoading(true)
        event.preventDefault()
        const { success, message, data } = await updatePost(newPost, avatar)
        setShowToast({
            show: true,
            message: message,
            type: success ? 'success' : 'danger',
        })
        if (success) {
            setNewPost({ ...newPost, avatar: post?.avatar })
            setShowUpdatePost(false)
            setUpdatingPost(data)
        }

        setUpdateLoading(false)
    }
    let body
    // if (postLoading) {
    //     body = <div>Loading</div>
    // } else
    body = (
        <Form onSubmit={onSubmit}>
            <Modal.Body>
                <Row>
                    <Col className="col-6">
                        <Form.Group className="mb-3">
                            <Form.Text id="title-help" muted>
                                Title
                            </Form.Text>
                            <Form.Control type="text" placeholder="Title" name="title" required aria-describedby="title-help" value={title} onChange={onChangeNewPostForm} />
                        </Form.Group>
                        <Form.Text id="title-help" muted>
                            Description
                        </Form.Text>

                        <Form.Group className="mb-3">
                            <Form.Control as="textarea" rows={3} placeholder="Description" name="description" value={description} onChange={onChangeNewPostForm} />
                        </Form.Group>
                        <Form.Group className="mb-3">
                            <Form.Text id="title-help" muted>
                                Salary
                            </Form.Text>
                            <Form.Control type="number" placeholder="Salary" name="salary" required value={salary} onChange={onChangeNewPostForm} />
                        </Form.Group>
                        <Form.Group className="mb-3">
                            <Form.Text id="title-help" muted>
                                Salary Type
                            </Form.Text>
                            <Form.Control type="text" placeholder="Salary Type" name="salaryType" required value={salaryType} onChange={onChangeNewPostForm} />
                        </Form.Group>
                        <Form.Group className="mb-3">
                            <Form.Text id="title-help" muted>
                                Recruit
                            </Form.Text>
                            <Form.Control type="number" placeholder="Recruit" name="recruit" required value={recruit} onChange={onChangeNewPostForm} />
                        </Form.Group>
                        <Form.Group className="mb-3">
                            <Form.Text id="title-help" muted>
                                Start Date
                            </Form.Text>
                            <Form.Control type="Date" placeholder="Start Date" name="startDate" aria-describedby="title-help" value={startDate} onChange={onChangeNewPostForm} />
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
                            <Form.Select name="fieldCode" value={fieldCode} onChange={onChangeNewPostForm}>
                                {fieldAndCity != null
                                    ? fieldAndCity[0].map((pfield) => (
                                          <option key={pfield.id} value={pfield.code}>
                                              {pfield.name}
                                          </option>
                                      ))
                                    : ''}
                            </Form.Select>
                        </Form.Group>
                        <Form.Group>
                            <Form.Label> City</Form.Label>
                            <Form.Select name="cityCode" value={cityCode} onChange={onChangeNewPostForm}>
                                {fieldAndCity != null
                                    ? fieldAndCity[1].map((city) => (
                                          <option key={city.id} value={city.code}>
                                              {city.name}
                                          </option>
                                      ))
                                    : ''}
                            </Form.Select>
                        </Form.Group>
                        <Form.Group>
                            <Form.Label> Service</Form.Label>
                            <Form.Select name="service" value={service} onChange={onChangeNewPostForm}>
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
                            <Form.Control type="text" placeholder="Location" name="location" required value={location} onChange={onChangeNewPostForm} />
                        </Form.Group>
                    </Col>
                    <Col className="col-6">
                        <img className="img-center img-add-post" id="update-img-review" src={post?.avatar} alt="img-avatar" />
                        <input className="center-block img-input-decorate" type="file" name="avatar" accept="image/*" onChange={onUploadFileChange} />
                    </Col>
                </Row>
            </Modal.Body>
            <Modal.Footer>
                <Button variant="secondary" onClick={closeDialog}>
                    Cancel
                </Button>
                <Button variant="primary" type="submit" disabled={updateLoading}>
                    {updateLoading && <span className="spinner-border spinner-border-sm mr-1"></span>}
                    Confirm
                </Button>
            </Modal.Footer>
        </Form>
    )

    return (
        <Modal className="modal-add-post" show={showUpdatePost} onHide={closeDialog}>
            <Modal.Header closeButton>
                <Modal.Title>Update Post</Modal.Title>
            </Modal.Header>
            {body}
        </Modal>
    )
}
export default UpdatePostModal
