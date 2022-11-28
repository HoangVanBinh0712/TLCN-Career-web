import { useContext, useState } from 'react'
import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col'
import '../../../css/EmployerProfile.css'
import { AuthContext } from '../../../../contexts/AuthContext'
import Button from 'react-bootstrap/Button'
import Form from 'react-bootstrap/Form'
import Modal from 'react-bootstrap/Modal'
import AlertMessage from '../../../layout/AlertMessage'
import { useQuery } from 'react-query'
import axios from 'axios'
import { apiUrl } from '../../../../contexts/constants'
import Spinner from 'react-bootstrap/esm/Spinner'
import avatarIcon from '../../../../assets/avatarIcon.png'

const EmpUpdateProfileModal = ({ user, showUpdate, setShowUpdate }) => {
    const { updateUserProfile } = useContext(AuthContext)
    const [updateLoading, setUpdateLoading] = useState(false)
    const [alert, setAlert] = useState(null)

    const [userInfo, setUserInfo] = useState({
        id: user.id,
        email: user.email,
        name: user ? user.name : '',
        phone: user ? user.phone : '',
        address: user ? user.address : '',
        fieldId: user ? user.field?.id : '',
        employee: user ? user.employee : '',
        description: user ? user.description : '',
        cityId: user ? user.city?.id : '',
        avatar: '',
    })

    const getCityAndField = async () => {
        try {
            const responseField = await axios.get(`${apiUrl}/field?page_number=1&limit=100`)
            const responseCity = await axios.get(`${apiUrl}/city`)

            return [responseField.data.data, responseCity.data.data]
        } catch (err) {
            console.log(err)
            return null
        }
    }
    const { data: fieldAndCity, status: fcStatus } = useQuery(['cityAndField'], async () => await getCityAndField())

    const closeDialog = () => {
        setShowUpdate(false)
    }
    const { email, name, phone, description, address, fieldId, employee, cityId, avatar } = userInfo

    const onChangeUserInfo = (event) =>
        setUserInfo({
            ...userInfo,
            [event.target.name]: event.target.value,
        })

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

    const onUploadFileChange = ({ target }) => {
        if (target.files < 1 || !target.validity.valid) {
            return
        }
        var preview = document.getElementById('img-review')

        var reader = new FileReader()

        reader.onloadend = function () {
            preview.src = reader.result
        }
        const file = target.files[0]
        if (file) {
            reader.readAsDataURL(file)
        } else {
            preview.src = avatarIcon
        }

        fileToBase64(file, (err, result) => {
            if (result) {
                setUserInfo({
                    ...userInfo,
                    avatar: file,
                })
            }
        })
    }

    const onSubmitUpdateProfile = async (event) => {
        setUpdateLoading(true)
        event.preventDefault()
        try {
            const profileUpdate = await updateUserProfile('employer', userInfo, avatar)
            if (profileUpdate.success) {
                setAlert({ type: 'success', message: 'Update successfull' })
                setTimeout(() => setAlert(null), 10000)
            } else {
                setAlert({ type: 'danger', message: profileUpdate.message })
                setTimeout(() => setAlert(null), 10000)
            }
        } catch (error) {
            console.log(error)
        }
        setUpdateLoading(false)
    }

    let body
    if (fcStatus === 'success')
        body = (
            <Modal className="modal-update-profile" show={showUpdate} onHide={closeDialog}>
                <Modal.Header closeButton>
                    <Modal.Title>Update Employer Profile</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <div className="form-profile-padding">
                        <Form onSubmit={onSubmitUpdateProfile}>
                            <Row>
                                <Row>
                                    <Col className="col-6">
                                        <AlertMessage info={alert} />
                                        <Form.Group>
                                            <Form.Label> Email</Form.Label>
                                            <Form.Control type="text" name="email" readOnly={true} value={email} onChange={onChangeUserInfo} />
                                        </Form.Group>
                                        <Form.Group>
                                            <Form.Label> Name</Form.Label>
                                            <Form.Control type="text" name="name" required value={name} onChange={onChangeUserInfo} />
                                        </Form.Group>
                                        <Form.Group>
                                            <Form.Label> Description</Form.Label>
                                            <Form.Control className='emp-description-textarea' as="textarea" rows="7" name="description" required value={description} onChange={onChangeUserInfo} />
                                        </Form.Group>
                                    </Col>
                                    <Col className="col-6 right-component">
                                        <h2>Avatar</h2>
                                        <img className="img-avatar" src={user.avatar ? user.avatar : avatarIcon} id="img-review" alt="avatar" />
                                    </Col>
                                </Row>
                                <Row className='form-profile-bottom-block'>
                                    <Col className="col-6">
                                        <Form.Group>
                                            <Form.Label> Phone</Form.Label>
                                            <Form.Control type="text" name="phone" required value={phone} onChange={onChangeUserInfo} />
                                        </Form.Group>
                                        <Form.Group>
                                            <Form.Label> Field</Form.Label>
                                            <Form.Select name="fieldId" value={fieldId} onChange={onChangeUserInfo}>
                                                {fieldAndCity[0].map((field) => (
                                                    <option key={field.id} value={field.id}>
                                                        {field.name}
                                                    </option>
                                                ))}
                                            </Form.Select>
                                        </Form.Group>

                                        <Form.Group>
                                            <Form.Label> City</Form.Label>
                                            <Form.Select name="cityId" value={cityId} onChange={onChangeUserInfo}>
                                                {fieldAndCity[1].map((city) => (
                                                    <option key={city.id} value={city.id}>
                                                        {city.name}
                                                    </option>
                                                ))}
                                            </Form.Select>
                                        </Form.Group>
                                    </Col>
                                    <Col className="col-6 form-profile-bottom-right-block">
                                        <Form.Group>
                                            <Form.Label> Avatar</Form.Label>
                                            <Form.Control name="avatar" type="file" className='input-avatar' accept="image/*" onChange={onUploadFileChange} />
                                        </Form.Group>
                                        <Form.Group>
                                            <Form.Label> Employee</Form.Label>
                                            <Form.Control type="number" name="employee" min="0" required value={employee} onChange={onChangeUserInfo} />
                                        </Form.Group>

                                        <Form.Group>
                                            <Form.Label> Addess</Form.Label>
                                            <Form.Control type="text" name="address" required value={address} onChange={onChangeUserInfo} />
                                        </Form.Group>
                                    </Col>
                                </Row>
                            </Row>

                            <Button
                                className="update-change-info-button"
                                variant="warning"
                                onClick={() => {
                                    closeDialog()
                                }}
                            >
                                Cancel
                            </Button>
                            <Button disabled={updateLoading} className="update-change-info-button" variant="success" type="submit">
                                {updateLoading && <span className="spinner-border spinner-border-sm mr-1"></span>}
                                Update
                            </Button>
                        </Form>
                    </div>
                </Modal.Body>
            </Modal>
        )
    else
        body = (
            <div className="d-flex justify-content-center mt-2">
                <Spinner animation="border" variant="info" />
            </div>
        )
    return body
}

export default EmpUpdateProfileModal
