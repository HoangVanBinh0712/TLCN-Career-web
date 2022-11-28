import Button from 'react-bootstrap/Button'
import Form from 'react-bootstrap/Form'
import { Link, useLocation } from 'react-router-dom'
import { useState, useContext, useEffect } from 'react'
import { AuthContext } from '../../contexts/AuthContext'
import AlertMessage from '../layout/AlertMessage'
import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col'
import * as queryString from 'query-string'
import Toast from 'react-bootstrap/Toast'

const EmployLoginForm = () => {
    const params = queryString.parse(window.location.search)

    const { loginEmployer } = useContext(AuthContext)
    const [showToast, setShowToast] = useState(false)

    const [authloading, setAuthLoading] = useState(false)
    const { state, pathname } = useLocation()

    //local state
    const [employerLoginForm, setUserLoginForm] = useState({
        username: '',
        password: '',
    })

    const [alert, setAlert] = useState(null)

    const { username, password } = employerLoginForm
    const onChangeEmployerLoginForm = (event) =>
        setUserLoginForm({
            ...employerLoginForm,
            [event.target.name]: event.target.value,
        })

    const employerLogin = async (event) => {
        setAuthLoading(true)
        event.preventDefault()
        try {
            const userLoginData = await loginEmployer(employerLoginForm)
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
    useEffect(() => {
        if (state && state.message) {
            setShowToast(true)
        }
    }, [state])
    let body

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
                        <Form className="my-4" id="form-login" onSubmit={employerLogin}>
                            <AlertMessage info={alert} />
                            <Form.Group>
                                <Form.Label>Username</Form.Label>
                                <Form.Control type="text" placeholder="" name="username" required value={username} onChange={onChangeEmployerLoginForm} />
                            </Form.Group>
                            <Form.Group>
                                <Form.Label>Password</Form.Label>
                                <Form.Control type="password" placeholder="" name="password" required value={password} onChange={onChangeEmployerLoginForm} />
                            </Form.Group>
                            <Button disabled={authloading} variant="success" type="submit" className="mt-2">
                                {authloading && <span className="spinner-border spinner-border-sm mr-1"></span>}
                                Login
                            </Button>
                        </Form>
                        <p>
                            Don't have an account?
                            <Link to="/employer/register">Register</Link>
                        </p>
                        <Link to="/user/login">
                            <Button variant="info">
                                Login with <strong>Employee</strong> account
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
                    <Link className="link-to-dashboard-36" to="#">
                        EMPLOYER
                    </Link>
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
export default EmployLoginForm
