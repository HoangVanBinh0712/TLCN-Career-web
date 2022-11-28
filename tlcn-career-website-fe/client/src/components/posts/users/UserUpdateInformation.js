import { useContext, useState } from 'react'
import Button from 'react-bootstrap/esm/Button'
import Col from 'react-bootstrap/esm/Col'
import Row from 'react-bootstrap/esm/Row'
import Form from 'react-bootstrap/Form'
import avatarIcon from '../../../assets/avatarIcon.png'
import { AuthContext } from '../../../contexts/AuthContext'
import AlertMessage from '../../layout/AlertMessage'

const UserUpdateInformation = ({ user, setUpdateLoading, setUpdate, updateLoading, data }) => {
    const [alert, setAlert] = useState(null)
    const { updateUserProfile } = useContext(AuthContext)
    const [userInfo, setUserInfo] = useState({
        id: user.id,
        email: user.email,
        name: user ? user.name : '',
        phone: user ? user.phone : '',
        address: user ? user.address : '',
        birth: user ? (user.birth ? user.birth.split(' ')[0] : '') : '',
        gender: user ? user.gender : '',
        cityId: data[0].id,
        avatar: '',
    })
    const { email, name, phone, address, birth, gender, cityId, avatar } = userInfo

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
            preview.src = ''
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
            const profileUpdate = await updateUserProfile('user', userInfo, avatar)
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

    return (
        <>
            <Row>
                <Col className="col-6">
                    <Form className="native-grid" onSubmit={onSubmitUpdateProfile}>
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
                            <Form.Label> Phone</Form.Label>
                            <Form.Control type="text" name="phone" required value={phone} onChange={onChangeUserInfo} />
                        </Form.Group>
                        <Form.Group>
                            <Form.Label> Addess</Form.Label>
                            <Form.Control type="text" name="address" required value={address} onChange={onChangeUserInfo} />
                        </Form.Group>
                        <Form.Group>
                            <Form.Label> Birth</Form.Label>
                            <Form.Control type="date" name="birth" value={birth} onChange={onChangeUserInfo} />
                        </Form.Group>
                        <Form.Group>
                            <Form.Label> Gender</Form.Label>
                            <Form.Control type="text" name="gender" placeholder="Ex: Male/Female/Orther" required value={gender} onChange={onChangeUserInfo} />
                        </Form.Group>
                        <Form.Group>
                            <Form.Label> City</Form.Label>
                            <Form.Select name="cityId" value={cityId} onChange={onChangeUserInfo}>
                                {data.map((city) => (
                                    <option key={city.id} value={city.id}>
                                        {city.name}
                                    </option>
                                ))}
                            </Form.Select>
                        </Form.Group>
                        <Form.Group>
                            <Form.Label> Avatar</Form.Label>
                            <Form.Control name="avatar" type="file" accept="image/*" onChange={onUploadFileChange} />
                        </Form.Group>

                        <Button
                            className="update-change-info-button"
                            variant="warning"
                            onClick={() => {
                                setUpdate(false)
                            }}
                        >
                            Cancel
                        </Button>
                        <Button disabled={updateLoading} className="update-change-info-button" variant="success" type="submit">
                            {updateLoading && <span className="spinner-border spinner-border-sm mr-1"></span>}
                            Update
                        </Button>
                    </Form>
                </Col>
                <Col className="col-6 img-avatar">
                    <img src={user.avatar ? user.avatar : avatarIcon} style={{ width: '90%', height: '60%' }} id="img-review" alt="img" />
                </Col>
            </Row>
        </>
    )
}

export default UserUpdateInformation
