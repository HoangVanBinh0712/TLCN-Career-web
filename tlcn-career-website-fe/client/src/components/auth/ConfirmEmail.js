import axios from 'axios'
import { useEffect } from 'react'
import { apiUrl } from '../../contexts/constants'
import swal from 'sweetalert'
import * as queryString from 'query-string'
import NavbarMenu from '../layout/NavbarMenu'
import Footer from '../layout/Footer'

const ConfirmEmail = () => {
    let params = queryString.parse(window.location.search)

    useEffect(() => {
        async function verify(user, email, code) {
            if (!user || !email || !code) return
            try {
                const response = await axios.post(`${apiUrl}/${user}/confirm-email?email=${email}&code=${code}`)
                if (response.data.success) {
                    swal('Success', 'Email confirm success !', 'success')
                } else {
                    swal('Error', 'Wrong code !', 'error')
                }
            } catch (error) {
                swal('Error', 'Wrong code !', 'error')
            }
        }
        verify(params.user, params.email, params.code)
    }, [])
    return (
        <>
            <NavbarMenu />
            <div style={{ width: '100%', height: '725px' }}></div>
            <Footer />
        </>
    )
}
export default ConfirmEmail
