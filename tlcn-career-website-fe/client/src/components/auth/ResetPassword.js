import axios from 'axios'
import { useState } from 'react'
import { apiUrl } from '../../contexts/constants'
import swal from 'sweetalert'
import * as queryString from 'query-string'
import NavbarMenu from '../layout/NavbarMenu'
import Footer from '../layout/Footer'
import Form from 'react-bootstrap/Form'
import Button from 'react-bootstrap/esm/Button'
import Row from 'react-bootstrap/esm/Row'
import Col from 'react-bootstrap/esm/Col'

const ResetPassword = () => {
    let params = queryString.parse(window.location.search)

    const [formData, setFormData] = useState({ code: params.code, newPassword: '', confirmNewPassword: '', email: params.email })
    const { newPassword, confirmNewPassword } = formData

    const onFormdataChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value })
    }

    return (
        <>
            <NavbarMenu />
            <div className="restpassword">
                <Row>
                    <Col className="col-7">
                        <img
                            src="https://preview.colorlib.com/theme/bootstrap/login-form-07/images/undraw_remotely_2j6y.svg"
                            style={{ maxHeight: '100%', maxWidth: '95%' }}
                            alt=""
                        />
                    </Col>
                    <Col className="col-5">
                        <Form
                            className="my-4"
                            id="form-login"
                            onSubmit={(e) => {
                                e.preventDefault()
                                async function verify(user, email, code) {
                                    if (!user || !email || !code) return
                                    if (formData.confirmNewPassword !== formData.newPassword) {
                                        swal('Error', 'Confirm password not match !', 'error')
                                        return
                                    }
                                    try {
                                        const response = await axios.post(`${apiUrl}/${user}/reset-password`, formData)
                                        if (response.data.success) {
                                            swal('Success', 'Change password success !', 'success')
                                            window.location.replace(`${user}/login`)
                                        } else {
                                            swal('Error', 'Wrong code !', 'error')
                                        }
                                    } catch (error) {
                                        swal('Error', 'Wrong code !', 'error')
                                    }
                                }
                                verify(params.user, params.email, params.code)
                            }}
                        >
                            <Form.Group className="mt-2">
                                <Form.Label>New password</Form.Label>
                                <Form.Control type="password" placeholder="New password" name="newPassword" value={newPassword} required onChange={onFormdataChange} />
                            </Form.Group>
                            <Form.Group className="mt-2">
                                <Form.Label>Confirm new password</Form.Label>
                                <Form.Control
                                    type="password"
                                    placeholder="Confirm new password"
                                    name="confirmNewPassword"
                                    required
                                    value={confirmNewPassword}
                                    onChange={onFormdataChange}
                                />
                            </Form.Group>
                            <Button className="mt-2" type="submit">
                                Confirm
                            </Button>
                        </Form>
                    </Col>
                </Row>
            </div>
            <Footer />
        </>
    )
}
export default ResetPassword
