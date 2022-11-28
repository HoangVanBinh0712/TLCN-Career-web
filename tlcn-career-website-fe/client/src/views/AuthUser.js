import UserLoginForm from '../components/auth/UserLoginForm'
import UserRegisterForm from '../components/auth/UserRegisterForm'
import { AuthContext } from '../contexts/AuthContext'
import { useContext } from 'react'
import Spiner from 'react-bootstrap/Spinner'

const AuthUser = ({ authRoute }) => {
    const {
        authState: { authLoading },
    } = useContext(AuthContext)

    let body

    if (authLoading)
        body = (
            <div style={{ width: '100%', hegiht: '100%', display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
                <div className="d-flex justify-content-center mt-2">
                    <Spiner animation="border" variant="info" />
                </div>
            </div>
        )
    else
        body = (
            <>
                {authRoute === 'user-login' && <UserLoginForm />}
                {authRoute === 'user-register' && <UserRegisterForm />}
            </>
        )

    return <div>{body}</div>
}

export default AuthUser
