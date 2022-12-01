import Button from 'react-bootstrap/Button'
import Form from 'react-bootstrap/Form'
import { Link } from 'react-router-dom'
import { JOBFIELD } from '../../contexts/constants'
import { useState, useContext, useEffect } from 'react'
import { AuthContext } from '../../contexts/AuthContext'
import AlertMessage from '../layout/AlertMessage'
import axios from 'axios'
import { apiUrl } from '../../contexts/constants'

const EmployerRegisterForm = () => {
    //context
    const { registerEmployer } = useContext(AuthContext)

    //local state
    const [empRegisterForm, setEmpRegisterForm] = useState({
        email: '',
        password: '',
        name: '',
        phone: '',
        field: 0,
        city: 0,
        address: '',
        employee: 0,
        confirmpassword: '',
    })

    const { email, password, name, phone, field, city, address, employee, confirmpassword } = empRegisterForm
    const onChangeEmpRegisterForm = (event) =>
        setEmpRegisterForm({
            ...empRegisterForm,
            [event.target.name]: event.target.value,
        })

    const [alert, setAlert] = useState(null)

    const [fields, setFields] = useState([])
    const [cities, setCities] = useState([])

    useEffect(() => {
        const getFields = async () => {
            const response = await axios.get(`${apiUrl}/field?&limit=50`)
            if (response.data.success) {
                setFields(response.data.data)
            }
        }
        const getCities = async () => {
            const response = await axios.get(`${apiUrl}/city`)
            if (response.data.success) {
                setCities(response.data.data)
            }
        }
        if (fields.length === 0) getFields()
        if (cities.length === 0) getCities()
    }, [])

    console.log(fields)

    const empRegister = async (event) => {
        event.preventDefault()
        if (confirmpassword !== password) {
            setAlert({ type: 'danger', message: 'You must re-enter the correct confirmation password' })
            setTimeout(() => setAlert(null), 10000)
        } else {
            try {
                const empRegisterData = await registerEmployer(empRegisterForm)
                if (empRegisterData.success) {
                    setAlert({ type: 'success', message: 'Account created successfully!' })
                    setTimeout(() => setAlert(null), 10000)
                } else {
                    setAlert({ type: 'danger', message: empRegisterData.message })
                    setTimeout(() => setAlert(null), 10000)
                }
                window.location.replace('/user/login')

            } catch (error) {
                console.log(error)
            }
        }
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
                    <span className="ute-login-sml-text">Please enter your business information</span>
                </div>
                <Form className="my-4" id="form-login" onSubmit={empRegister}>
                    <AlertMessage info={alert} />
                    <Form.Group>
                        <Form.Label>Name</Form.Label>
                        <Form.Control type="text" placeholder="" name="name" required value={name} onChange={onChangeEmpRegisterForm} />
                    </Form.Group>
                    <Form.Group>
                        <Form.Label>Phone</Form.Label>
                        <Form.Control type="text" placeholder="" name="phone" required value={phone} onChange={onChangeEmpRegisterForm} />
                    </Form.Group>
                    <Form.Group>
                        <Form.Label>Addess </Form.Label>
                        <Form.Control type="text" placeholder="" name="address" required value={address} onChange={onChangeEmpRegisterForm} />
                    </Form.Group>
                    <Form.Group>
                        <Form.Label>Field </Form.Label>
                        <br></br>
                        <select className="select-fields" name="field" onChange={onChangeEmpRegisterForm}>
                            <option key={''} value="" defaultChecked>
                                Select Field Location
                            </option>
                            {fields.map((field) => (
                                <option key={field.id} value={field.id}>
                                    {field.name}
                                </option>
                            ))}
                        </select>
                    </Form.Group>
                    <Form.Group>
                        <Form.Label>City </Form.Label>
                        <br></br>
                        <select className="select-fields" name="city" onChange={onChangeEmpRegisterForm}>
                            <option key={''} value="" defaultChecked>
                                Select City Location
                            </option>
                            {cities.map((city) => (
                                <option key={city.id} value={city.id}>
                                    {city.name}
                                </option>
                            ))}
                        </select>
                    </Form.Group>
                    <Form.Group>
                        <Form.Label>Employee</Form.Label>
                        <Form.Control type="number" placeholder="" name="employee" required value={employee} onChange={onChangeEmpRegisterForm} />
                    </Form.Group>
                    <Form.Group>
                        <Form.Label>Email</Form.Label>
                        <Form.Control type="text" placeholder="" name="email" required value={email} onChange={onChangeEmpRegisterForm} />
                    </Form.Group>
                    <Form.Group>
                        <Form.Label>Password</Form.Label>
                        <Form.Control type="password" placeholder="" name="password" required value={password} onChange={onChangeEmpRegisterForm} />
                    </Form.Group>
                    <Form.Group>
                        <Form.Label>Confirm password</Form.Label>
                        <Form.Control type="password" placeholder="" name="confirmpassword" required value={confirmpassword} onChange={onChangeEmpRegisterForm} />
                    </Form.Group>
                    <Button className="mt-2" variant="success" type="submit">
                        Register
                    </Button>
                </Form>
                <p>
                    {' '}
                    Aready have an account?
                    <Link to="/employer/login">Login</Link>
                </p>
                <Link to="/user/register">
                    <Button variant="info">Create an employee account</Button>
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
                        EMPLOYER
                    </Link>
                </div>
            </div>
            {/*Form login*/}
            {body}
        </>
    )
}
export default EmployerRegisterForm
