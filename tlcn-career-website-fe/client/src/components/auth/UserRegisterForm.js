import Button from 'react-bootstrap/Button'
import Form from 'react-bootstrap/Form'
import { Link } from 'react-router-dom'
import { useState, useContext } from 'react'
import { AuthContext } from '../../contexts/AuthContext'
import AlertMessage from '../layout/AlertMessage'

const UserRegisterForm = () => {
    //context
    const { registerUser } = useContext(AuthContext)

    //local state
    const [userRegisterForm, setUserRegisterForm] = useState({
        email: '',
        password: '',
        name: '',
        phone: '',
        address: '',
        confirmpassword: '',
    })
    const [updateLoading, setUpdateLoading] = useState(false)

    const { email, password, name, phone, address, confirmpassword } = userRegisterForm
    const onChangeUserRegisterForm = (event) =>
        setUserRegisterForm({
            ...userRegisterForm,
            [event.target.name]: event.target.value,
        })

    const [alert, setAlert] = useState(null)

    const userRegister = async (event) => {
        event.preventDefault()
        setUpdateLoading(true)
        if (confirmpassword !== password) {
            setAlert({ type: 'danger', message: 'You must re-enter the correct confirmation password' })
            setTimeout(() => setAlert(null), 10000)
        } else {
            try {
                const userResgisterData = await registerUser(userRegisterForm)
                if (userResgisterData.success) {
                    setAlert({ type: 'success', message: 'Account created successfully!' })
                    setTimeout(() => setAlert(null), 10000)
                    window.location.replace('/user/login')
                } else {
                    setAlert({ type: 'danger', message: userResgisterData.message })
                    setTimeout(() => setAlert(null), 10000)
                }
            } catch (error) {
                console.log(error)
            }
        }
        setUpdateLoading(false)
    }

    /* if(password !== confirmpassword){
            setAlert({type:'danger', message: 'Password do not match!'})
            setTimeout(()=> setAlert(null), 10000)
            return
        } */

    let body

    body = (
        <>
            <div className="grid login-grid main login-box">
                <div className="ute-title">
                    <h3>Sign up to continue!</h3>
                    <div className="login-social"></div>
                    <span className="ute-login-sml-text">Please enter your information</span>
                </div>
                <Form className="my-4" id="form-login" onSubmit={userRegister}>
                    <AlertMessage info={alert} />
                    <Form.Group>
                        <Form.Label>Name</Form.Label>
                        <Form.Control type="text" placeholder="" name="name" required value={name} onChange={onChangeUserRegisterForm} />
                    </Form.Group>
                    <Form.Group>
                        <Form.Label>Phone</Form.Label>
                        <Form.Control type="text" placeholder="" name="phone" required value={phone} onChange={onChangeUserRegisterForm} />
                    </Form.Group>
                    <Form.Group>
                        <Form.Label>Address </Form.Label>
                        <Form.Control type="text" placeholder="" name="address" required value={address} onChange={onChangeUserRegisterForm} />
                    </Form.Group>
                    <Form.Group>
                        <Form.Label>Email</Form.Label>
                        <Form.Control type="text" placeholder="" name="email" required value={email} onChange={onChangeUserRegisterForm} />
                    </Form.Group>
                    <Form.Group>
                        <Form.Label>Password</Form.Label>
                        <Form.Control type="password" placeholder="" name="password" required value={password} onChange={onChangeUserRegisterForm} />
                    </Form.Group>
                    <Form.Group>
                        <Form.Label>Confirm password</Form.Label>
                        <Form.Control type="password" placeholder="" name="confirmpassword" required value={confirmpassword} onChange={onChangeUserRegisterForm} />
                    </Form.Group>
                    <Button className="mt-2" variant="success" type="submit" disabled={updateLoading}>
                        {updateLoading && <span className="spinner-border spinner-border-sm mr-1"></span>}
                        Register
                    </Button>
                </Form>
                <p>
                    Aready have an account?
                    <Link to="/user/login">Login</Link>
                </p>
                <Link to="/employer/register">
                    <Button variant="info">Create an employer account</Button>
                </Link>
            </div>
        </>
    )

    return (
        <>
            {/*Header,logo*/}
            <div className="utew-login-top-header">
                <div className="logo-box-login">
                    <Link className="link-to-dashboard-36" to="/dashboard">
                        EMPLOYEE
                    </Link>
                </div>
            </div>
            {/*Form login*/}
            {body}
        </>
    )
}
export default UserRegisterForm
