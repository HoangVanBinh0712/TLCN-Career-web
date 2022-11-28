import Modal from 'react-bootstrap/Modal'
import Form from 'react-bootstrap/Form'
import { useContext, useState } from 'react'
import AlertMessage from '../../layout/AlertMessage'
import Button from 'react-bootstrap/esm/Button'
import { AuthContext } from '../../../contexts/AuthContext'

const ChangePasswordModal = ({ showChangePassword, setShowChangePassword }) => {
    const { changePassword } = useContext(AuthContext)
    const [alert, setAlert] = useState(null)
    const [updateLoading, setUpdateLoading] = useState(false)

    const [password, setPassword] = useState({
        oldPassword: '',
        newPassword: '',
        confirmNewPassword: '',
    })

    const { oldPassword, newPassword, confirmNewPassword } = password

    const onChangePassword = (event) => {
        setPassword({ ...password, [event.target.name]: event.target.value })
    }
    const onCloseModalPassword = () => {
        setPassword({
            oldPassword: '',
            newPassword: '',
            confirmNewPassword: '',
        })
        setShowChangePassword(false)
    }
    const onSubmitChangePassword = async (event) => {
        event.preventDefault()

        if (newPassword !== confirmNewPassword) {
            setAlert({ type: 'danger', message: 'Confirm password not match !' })
            setTimeout(() => setAlert(null), 10000)
        } else {
            setUpdateLoading(true)
            event.preventDefault()
            const data = {
                newPassword: newPassword,
                oldPassword: oldPassword,
            }
            try {
                const response = await changePassword('user', data)
                console.log(response)
                setAlert({
                    type: response.success ? 'success' : 'danger',
                    message: response.message,
                })
                setTimeout(() => setAlert(null), 10000)
            } catch (error) {
                console.log(error)
            }
            setUpdateLoading(false)
        }
    }
    return (
        <Modal show={showChangePassword} onHide={onCloseModalPassword}>
            <Modal.Header closeButton>
                <Modal.Title>Change Password</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Form onSubmit={onSubmitChangePassword}>
                    <Form.Group>
                        <AlertMessage info={alert} />
                        <Form.Label> Old Password</Form.Label>
                        <Form.Control type="password" name="oldPassword" value={oldPassword} onChange={onChangePassword} required minLength={6} />
                    </Form.Group>
                    <Form.Group>
                        <Form.Label> New Password</Form.Label>
                        <Form.Control type="password" name="newPassword" value={newPassword} onChange={onChangePassword} required minLength={6} />
                    </Form.Group>
                    <Form.Group>
                        <Form.Label> Confirm New Password</Form.Label>
                        <Form.Control type="password" name="confirmNewPassword" value={confirmNewPassword} onChange={onChangePassword} required minLength={6} />
                    </Form.Group>
                    <Button disabled={updateLoading} type="submit" className="update-change-info-button mt-3" variant="success">
                        {updateLoading && <span className="spinner-border spinner-border-sm mr-1"></span>}
                        Confirm
                    </Button>
                    <Button
                        className="update-change-info-button mt-3"
                        variant="warning"
                        onClick={() => {
                            onCloseModalPassword()
                        }}
                    >
                        Cancel
                    </Button>
                </Form>
            </Modal.Body>
        </Modal>
    )
}

export default ChangePasswordModal
