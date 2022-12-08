import Button from 'react-bootstrap/Button'
import Form from 'react-bootstrap/Form'
import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col'

import { Link, useLocation } from 'react-router-dom'
import { useState, useContext } from 'react'
import { AuthContext } from '../../contexts/AuthContext'
import AlertMessage from '../layout/AlertMessage'
import Toast from 'react-bootstrap/Toast'
import { useEffect } from 'react'
import * as queryString from 'query-string'

const UserLoginForm = () => {
    const params = queryString.parse(window.location.search)

    //context
    const { loginUser } = useContext(AuthContext)
    const [authloading, setAuthLoading] = useState(false)

    //local state
    const [userLoginForm, setUserLoginForm] = useState({
        username: '',
        password: '',
    })

    const [alert, setAlert] = useState(null)
    const [showToast, setShowToast] = useState(false)

    const { state, pathname } = useLocation()

    const { username, password } = userLoginForm
    const onChangeUserLoginForm = (event) =>
        setUserLoginForm({
            ...userLoginForm,
            [event.target.name]: event.target.value,
        })

    const userLogin = async (event) => {
        setAuthLoading(true)
        event.preventDefault()
        try {
            const userLoginData = await loginUser(userLoginForm)
            if (!userLoginData.success) {
                setAlert({ type: 'danger', message: userLoginData.message })
                setTimeout(() => setAlert(null), 10000)
            } else {
                if (params['redirect']) window.location.replace(params['redirect'])
                else window.location.replace('/dashboard')
            }
        } catch (error) {
            console.log(error)
        }
        setAuthLoading(false)
    }

    let body
    useEffect(() => {
        if (state && state.message) {
            setShowToast(true)
        }
    }, [state])
    body = (
        <>
            <div className="grid login-grid main login-box">
                <Row>
                    <Col className="col-7">
                        <img
                            src="https://preview.colorlib.com/theme/bootstrap/login-form-07/images/undraw_remotely_2j6y.svg"
                            style={{ maxHeight: '100%', maxWidth: '95%' }}
                            alt=""
                        />
                    </Col>
                    <Col className="col-5">
                        <div className="ute-title">
                            <h3>Sign in to continue!</h3>
                            <div className="login-social"></div>
                        </div>
                        <Form className="my-4" id="form-login" onSubmit={userLogin}>
                            <AlertMessage info={alert} />
                            <Form.Group>
                                <Form.Label>Username</Form.Label>
                                <Form.Control type="text" placeholder="" name="username" required value={username} onChange={onChangeUserLoginForm} />
                            </Form.Group>
                            <Form.Group>
                                <Form.Label>Password</Form.Label>
                                <Form.Control type="password" placeholder="" name="password" required value={password} onChange={onChangeUserLoginForm} />
                            </Form.Group>
                            <Button disabled={authloading} variant="success" type="submit" className="mt-2">
                                {authloading && <span className="spinner-border spinner-border-sm mr-1"></span>}
                                Login
                            </Button>
                        </Form>
                        <p>
                            Don't have an account?
                            <Link to="/user/register">Register</Link>
                        </p>
                        <p>
                            <Link to="/send-verify?user=user"> Foget password ?</Link>
                        </p>
                        <Link to="/employer/login">
                            <Button variant="info">
                                Login with <strong>Employer</strong> account
                            </Button>
                        </Link>
                    </Col>
                </Row>
            </div>
        </>
    )

    return (
        <>
            {/*Header,logo*/}
            <div className="utew-login-top-header">
                <div className="logo-box-login">
                    <p className="link-to-dashboard-36">EMPLOYEE</p>
                </div>
            </div>
            {/*Form login*/}
            <Toast
                show={showToast}
                style={{ position: 'fixed', top: '20%', right: '10px' }}
                className={`bg-danger text-white`}
                delay={3000}
                onClose={() => {
                    window.history.replaceState(pathname, null)
                    setShowToast(false)
                }}
                autohide
            >
                <Toast.Body>
                    <strong>{state?.message}</strong>
                </Toast.Body>
            </Toast>
            {body}
        </>
    )
}
export default UserLoginForm
