import { Route } from 'react-router-dom'
import NavbarMenu from '../layout/NavbarMenu'
import Footer from '../layout/Footer'

const UnloginRoute = ({ component: Component, ...rest }) => {
    return (
        <Route
            {...rest}
            render={(props) => (
                <>
                    <NavbarMenu />
                    <Component {...rest} {...props} />
                    <Footer />
                </>
            )}
        />
    )
}

export default UnloginRoute
