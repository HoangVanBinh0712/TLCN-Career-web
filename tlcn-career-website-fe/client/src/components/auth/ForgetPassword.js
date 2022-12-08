import Button from 'react-bootstrap/esm/Button'
import Col from 'react-bootstrap/esm/Col'
import Row from 'react-bootstrap/esm/Row'
import { Link } from 'react-router-dom'
import Form from 'react-bootstrap/Form'
import { useState } from 'react'
import axios from 'axios'
import { apiUrl } from '../../contexts/constants'
import swal from 'sweetalert'
import * as queryString from 'query-string'

const ForgetPassword = () => {
    let params = queryString.parse(window.location.search)

    const [authloading, setAuthLoading] = useState(false)
    const [email, setEmail] = useState('')

    const sendResetPass = async (event) => {
        setAuthLoading(true)
        event.preventDefault()
        try {
            if (email === '') {
                swal('Error', 'Email must not be empty !', 'error')
                return
            }
            const response = await axios.get(`${apiUrl}/send-${params.user}-reset-password-code?email=${email}`)
            if (response.data.success) {
                swal('Success', response.data.message, 'success')
            } else {
                swal('Error', 'Some thing went wrong please try again !', 'error')
            }
        } catch (error) {
            swal('Error', 'Some thing went wrong please try again !', 'error')
        }

        setAuthLoading(false)
    }
    let body = (
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
                        <Form className="my-4" id="form-login" onSubmit={sendResetPass}>
                            <Form.Group>
                                <Form.Label>Email</Form.Label>
                                <Form.Control
                                    type="email"
                                    placeholder=""
                                    name="email"
                                    required
                                    value={email}
                                    onChange={(e) => {
                                        setEmail(e.target.value)
                                    }}
                                />
                            </Form.Group>

                            <Button disabled={authloading} variant="success" type="submit" className="mt-2">
                                {authloading && <span className="spinner-border spinner-border-sm mr-1"></span>}
                                Send verify code
                            </Button>
                        </Form>
                    </Col>
                </Row>
            </div>
        </>
    )

    return (
        <>
            <div className="utew-login-top-header">
                <div className="logo-box-login">
                    <Link className="link-to-dashboard-36" to="#">
                        EMPLOYER
                    </Link>
                </div>
            </div>

            {body}
        </>
    )
}
export default ForgetPassword
