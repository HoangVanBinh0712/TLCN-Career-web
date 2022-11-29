import React from 'react'
import Navbar from 'react-bootstrap/Navbar'
import Nav from 'react-bootstrap/Nav'
import logoutIcon from '../../assets/people-icon.png'
import Button from 'react-bootstrap/esm/Button'
import { Link } from 'react-router-dom'
import { useContext } from 'react'
import { AuthContext } from '../../contexts/AuthContext'

const NavbarMenu = () => {
    const {
        authState: { user, isAuthenticated, isUser, isEmployer, isAdmin },
        logoutSection,
    } = useContext(AuthContext)

    const logout = (e) => {
        e.preventDefault()
        let replaceUrl = ''
        if (isUser) replaceUrl = '/user/'
        else if (isEmployer) replaceUrl = '/employer/'
        else if (isAdmin) replaceUrl = '/admin/'

        window.location.replace(replaceUrl + 'login')

        logoutSection()
    }

    let body

    if (isAuthenticated && isUser) {
        body = (
            <Navbar.Collapse id="basic-navbar-nar">
                <Nav className="mr-auto">
                    <Nav.Link className="font-weigth-border link-to-dashboard-20" to="/profile" as={Link}>
                        Profile
                    </Nav.Link>
                    <Nav.Link className="font-weigth-border link-to-dashboard-20" to="/service" as={Link}>
                        Service
                    </Nav.Link>
                </Nav>
                <Nav className="sc-geuGuN cpxZcn rightNavigation-homepage">
                    <Nav.Link className="font-weigth-border link-to-dashboard-20" to="/employer/login" as={Link}>
                        EMPLOYER
                    </Nav.Link>
                    <Button
                        variant="secondary"
                        className="font-weigth-border text-white"
                        onClick={(e) => {
                            logout(e)
                        }}
                    >
                        <img src={logoutIcon} alt="img" width="32" height="32" className="mr-2" />
                        Logout
                    </Button>
                </Nav>
            </Navbar.Collapse>
        )
    } else if (isAuthenticated && isEmployer) {
        body = (
            <Navbar.Collapse id="basic-navbar-nar">
                <Nav className="mr-auto">
                    <Nav.Link className="font-weigth-border link-to-dashboard-20" to={'/employer/profile/' + user.id} as={Link}>
                        Profile
                    </Nav.Link>
                    <Nav.Link className="font-weigth-border link-to-dashboard-20" to="/service" as={Link}>
                        Service
                    </Nav.Link>
                    <Nav.Link className="font-weigth-border link-to-dashboard-20" to="/employer/posts" as={Link}>
                        Your Posts
                    </Nav.Link>
                </Nav>
                <Nav className="sc-geuGuN cpxZcn rightNavigation-homepage">
                    <Nav.Link className="font-weigth-border link-to-dashboard-20" to="/user/login" as={Link}>
                        Employee
                    </Nav.Link>
                    <Button
                        variant="secondary"
                        className="font-weigth-border text-white"
                        onClick={(e) => {
                            logout(e)
                        }}
                    >
                        <img src={logoutIcon} alt="img" width="32" height="32" className="mr-2" />
                        Logout
                    </Button>
                </Nav>
            </Navbar.Collapse>
        )
    } else if (isAuthenticated && isAdmin) {
        body = (
            <Navbar.Collapse id="basic-navbar-nar">
                <Nav className="mr-auto">
                    <Nav.Link className="font-weigth-border link-to-dashboard-20" to="/admin/profile" as={Link}>
                        Profile
                    </Nav.Link>
                    <Nav.Link className="font-weigth-border link-to-dashboard-20" to="/service" as={Link}>
                        Service
                    </Nav.Link>
                    <Nav.Link className="font-weigth-border link-to-dashboard-20" to="/admin/posts?page=1&limit=9&status=ACTIVE" as={Link}>
                        Job Manage
                    </Nav.Link>
                    <Button
                        variant="secondary"
                        className="font-weigth-border text-white"
                        onClick={(e) => {
                            logout(e)
                        }}
                    >
                        <img src={logoutIcon} alt="img" width="32" height="32" className="mr-2" />
                        Logout
                    </Button>
                </Nav>
            </Navbar.Collapse>
        )
    } else {
        body = (
            <Navbar.Collapse id="basic-navbar-nar">
                <Nav className="mr-auto">
                    <Nav.Link className="font-weigth-border link-to-dashboard-20" to="/service" as={Link}>
                        Service
                    </Nav.Link>
                </Nav>

                <Nav className="sc-geuGuN cpxZcn rightNavigation-homepage">
                    <Nav.Link className="font-weigth-border link-to-dashboard-20" to="/user/login" as={Link}>
                        Sign in
                    </Nav.Link>
                </Nav>
            </Navbar.Collapse>
        )
    }

    return (
        <>
            <Navbar expand="lg" bg="primary" variant="dark" className="sc-fjqEFS cOCOrx menu-homepage">
                <Navbar.Brand className="font-weigth-border text-white">
                    <Nav.Link className="link-to-dashboard-24" to="/dashboard" as={Link}>
                        DashBoard
                    </Nav.Link>
                </Navbar.Brand>
                <Navbar.Toggle aria-controls="basic-navbar-nar" />
                {body}
            </Navbar>
        </>
    )
}

export default NavbarMenu
