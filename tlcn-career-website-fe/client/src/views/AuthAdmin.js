import { AuthContext } from '../contexts/AuthContext'
import { useContext } from 'react'
import { Redirect } from 'react-router-dom'
import Spiner from 'react-bootstrap/Spinner'
import AdminLoginForm from '../components/auth/AdminLogin'

const AuthAdmin = ({ authRoute }) => {
    const {
        authState: { authLoading, isAuthenticated, isAdmin },
    } = useContext(AuthContext)

    let body

    if (authLoading)
        body = (
            <div className="d-flex justify-content-center mt-2">
                <Spiner animation="border" variant="info" />
            </div>
        )
    else if (isAuthenticated && isAdmin) return <Redirect to="/admin/posts" />
    else body = <>{authRoute === 'admin-login' && <AdminLoginForm />}</>

    return <div>{body}</div>
}

export default AuthAdmin
