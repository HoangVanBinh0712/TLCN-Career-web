import { useContext, useEffect, useState } from 'react'
import { EmployerPostContext } from '../../../contexts/EmployerPostContext'
import Row from 'react-bootstrap/Row'
import Spiner from 'react-bootstrap/Spinner'
import Toast from 'react-bootstrap/Toast'
import { MDBRadio, MDBBtnGroup } from 'mdb-react-ui-kit'
import NoPostFound from '../../NoPostFound'
import '../../css/EmpPost.css'
import EmployerSingleOrder from './EmployerSingleOrder'
import PostPaging from '../../PostPaging'
import Dropdown from 'react-bootstrap/Dropdown'

const EmpOrder = () => {
    const orderStatus = ['WAIT_FOR_PAYMENT', 'PAID']
    const {
        getOrders,
        showToast: { show, message, type },
        setShowToast,
    } = useContext(EmployerPostContext)
    const [orders, setOrders] = useState({ data: [], currentPage: 0, totalPage: 0 })
    const [orderLoading, setOrderLoading] = useState(true)
    const [oStatus, setOStatus] = useState('PAID')
    const [url, setUrl] = useState(window.location)

    useEffect(() => {
        async function getListOrder(params) {
            setOrderLoading(true)
            const lstOrder = await getOrders(`${params}`)

            setOrders({ data: lstOrder.data, currentPage: lstOrder.currentPage, totalPage: lstOrder.totalPage })
            setOrderLoading(false)
        }
        const queryParameters = new URLSearchParams(window.location.search)
        const status = queryParameters.get('status') ? queryParameters.get('status') : 'PAID'
        const page = queryParameters.get('page') ? queryParameters.get('page') : 1
        const limit = queryParameters.get('limit') ? queryParameters.get('limit') : 9

        let params = ''
        if (status) params += `status=${status}&`
        params += `page=${page}&`
        params += `limit=${limit}&`
        getListOrder(params)
        setOStatus(status)
    }, [url])

    function setGetParam(params) {
        var newUrl = window.location.origin + window.location.pathname + '?' + params
        window.history.pushState({ path: newUrl }, '', newUrl)
        setUrl(newUrl)
    }

    const handlePageChange = (pageNumber, status, keyword) => {
        const params = new URLSearchParams(window.location.search)

        if (pageNumber) {
            params.set('page', pageNumber)
            params.set('limit', 9)
        } else {
            params.set('page', 1)
            params.set('limit', 9)
        }
        if (status) params.set('status', status)
        setGetParam(params.toString())
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
                        <Dropdown>
                            <Dropdown.Toggle variant="success" id="dropdown-basic">
                                {oStatus}
                            </Dropdown.Toggle>
                            <Dropdown.Menu>
                                {orderStatus.map((st) => (
                                    <Dropdown.Item
                                        key={st}
                                        onClick={() => {
                                            setOStatus(st)
                                            handlePageChange(null, st, null)
                                        }}
                                    >
                                        {st}
                                    </Dropdown.Item>
                                ))}
                            </Dropdown.Menu>
                        </Dropdown>
                    </div>
                    {orders.data.length === 0 ? (
                        <NoPostFound />
                    ) : (
                        <>
                            {orders.data.map((order) => (
                                <Row key={order.id} className="list-posts row-cols-1 row-cols-md-3 g-4 mx-auto main-row">
                                    <EmployerSingleOrder order={order} />
                                </Row>
                            ))}
                            <PostPaging handlePageChange={handlePageChange} currentPage={orders.currentPage} totalPage={orders.totalPage} />
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
