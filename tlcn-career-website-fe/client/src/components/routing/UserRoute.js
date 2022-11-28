import { Route, Redirect } from 'react-router-dom'
import { useContext } from 'react'
import React from 'react'
import { AuthContext } from '../../contexts/AuthContext'
import Spinner from 'react-bootstrap/Spinner'
import NavbarMenu from '../layout/NavbarMenu'
import Footer from '../layout/Footer'

const UserRoute = ({ component: Component, ...rest }) => {
    const {
        authState: { authloading, isAuthenticated, isUser },
    } = useContext(AuthContext)

    const url = window.location.pathname + '?' + window.location.search
    const query = `?redirect=${url}`
    if (authloading) {
        return (
            <div className="spiner-container">
                <Spinner animation="border" variant="info" />
            </div>
        )
    }

    return (
        <Route
            {...rest}
            render={(props) =>
                isAuthenticated && isUser ? (
                    <>
                        <NavbarMenu />
                        <Component {...rest} {...props} />
                        <Footer />
                    </>
                ) : (
                    <Redirect
                        push
                        to={{
                            pathname: `/user/login`,
                            search: query,
                            state: { message: 'You do not have permission to access !' },
                        }}
                    />
                )
            }
        />
    )
}

export default UserRoute
