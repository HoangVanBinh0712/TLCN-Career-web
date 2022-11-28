import { useContext, useEffect, useState } from 'react'
import { EmployerPostContext } from '../../../contexts/EmployerPostContext'
import Row from 'react-bootstrap/Row'
import Spiner from 'react-bootstrap/Spinner'
import Toast from 'react-bootstrap/Toast'
import { MDBRadio, MDBBtnGroup } from 'mdb-react-ui-kit'
import NoPostFound from '../../NoPostFound'
import '../../css/EmpPost.css'
import EmployerSingleOrder from './EmployerSingleOrder'

const EmpOrder = () => {
    const orderStatus = ['WAIT_FOR_PAYMENT', 'PAID']
    const {
        getOrders,
        showToast: { show, message, type },
        setShowToast,
    } = useContext(EmployerPostContext)
    const [orders, setOrders] = useState([])
    const [orderLoading, setOrderLoading] = useState(true)
    const [oStatus, setOStatus] = useState('WAIT_FOR_PAYMENT')

    useEffect(() => {
        async function getListOrder(status) {
            setOrderLoading(true)
            setOStatus(status)
            const lstOrder = await getOrders(`status=${status}&page=1&limit=10`)
            setOrders(lstOrder)
            setOrderLoading(false)
        }
        const queryParameters = new URLSearchParams(window.location.search)
        const status = queryParameters.get('status')
        if (status) getListOrder(status)
        else getListOrder('WAIT_FOR_PAYMENT')
    }, [window.location.search])

    const handleStatusChange = async (status) => {
        var newUrl = window.location.origin + window.location.pathname + '?status=' + status
        window.history.pushState({ path: newUrl }, '', newUrl)
    }
    let body = null

    if (orderLoading) {
        body = (
            <div className="emp-post-page d-flex justify-content-center mt-2">
                <Spiner animation="border" variant="info" />
            </div>
        )
    } else {
        body = (
            <>
                <div className="container emp-post-page">
                    <div className="mt-2 banner">
                        <MDBBtnGroup className="banner-buttons">
                            {orderStatus.map((status) => {
                                return (
                                    <MDBRadio
                                        key={status}
                                        className="banner-button"
                                        id="btn-radio"
                                        name="options"
                                        wrapperTag="span"
                                        label={status}
                                        checked={oStatus === status}
                                        onChange={() => {
                                            setOStatus(status)
                                            handleStatusChange(status)
                                        }}
                                    />
                                )
                            })}
                        </MDBBtnGroup>
                    </div>
                    {orders.length === 0 ? (
                        <NoPostFound />
                    ) : (
                        <>
                            {orders.map((order) => (
                                <Row key={order.id} className="list-posts row-cols-1 row-cols-md-3 g-4 mx-auto main-row">
                                    <EmployerSingleOrder order={order} />
                                </Row>
                            ))}
                        </>
                    )}
                    <Toast
                        show={show}
                        style={{ position: 'fixed', top: '20%', right: '10px' }}
                        className={`bg-${type} text-white`}
                        onClose={setShowToast.bind(this, {
                            show: false,
                            message: '',
                            type: null,
                        })}
                        delay={3000}
                        autohide
                    >
                        <Toast.Body>
                            <strong>{message}</strong>
                        </Toast.Body>
                    </Toast>
                </div>
            </>
        )
    }

    return body
}

export default EmpOrder
