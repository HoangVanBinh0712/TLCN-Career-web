import { useContext, useEffect, useState } from 'react'
import { EmployerPostContext } from '../../contexts/EmployerPostContext'
import Row from 'react-bootstrap/Row'
import Spiner from 'react-bootstrap/Spinner'
import Toast from 'react-bootstrap/Toast'
import NoPostFound from '../NoPostFound'
import '../css/Service.css'
import axios from 'axios'
import { apiUrl } from '../../contexts/constants'
import SingleService from './SingleService'
import Col from 'react-bootstrap/esm/Col'
import { AuthContext } from '../../contexts/AuthContext'
import OverlayTrigger from 'react-bootstrap/esm/OverlayTrigger'
import Button from 'react-bootstrap/esm/Button'
import AddServiceModal from '../posts/admin/AddServiceModal'
import Tooltip from 'react-bootstrap/esm/Tooltip'
import addIcon from '../../assets/plus-circle-fill.svg'
import UpdateServiceModal from '../posts/admin/UpdateServiceModal'

const Service = () => {
    const {
        showToast: { show, message, type },
        setShowToast,
    } = useContext(EmployerPostContext)

    const {
        authState: { isAdmin },
    } = useContext(AuthContext)
    const [services, setServices] = useState([])
    const [serviceLoading, setServiceLoading] = useState(true)
    const [showServiceModal, setShowServiceModal] = useState(false)
    const [showUpdateServiceModal, setShowUpdateServiceModal] = useState(false)

    const [updatingService, setUpdatingService] = useState(null)
    useEffect(() => {
        async function getServices() {
            setServiceLoading(true)
            const lstService = await axios.get(`${apiUrl}/service`)
            setServices(lstService.data.data)
            setServiceLoading(false)
        }
        getServices()
    }, [])

    let body = null

    if (serviceLoading) {
        body = (
            <div className="service-page d-flex justify-content-center mt-2">
                <Spiner animation="border" variant="info" />
            </div>
        )
    } else {
        body = (
            <>
                <div className="container service-page">
                    {services.length === 0 ? (
                        <NoPostFound />
                    ) : (
                        <>
                            <Row className="list-services row-cols-1 row-cols-md-3 g-4 mx-auto main-row">
                                {services.map((service) => (
                                    <Col className="my-2 pad-5 premium-service-border -box">
                                        <SingleService
                                            service={service}
                                            isAdmin={isAdmin}
                                            setUpdatingService={setUpdatingService}
                                            setShowUpdateServiceModal={setShowUpdateServiceModal}
                                        />
                                    </Col>
                                ))}
                            </Row>
                        </>
                    )}
                    {isAdmin && showServiceModal && (
                        <AddServiceModal showServiceModal={showServiceModal} setShowServiceModal={setShowServiceModal} setServices={setServices} services={services} />
                    )}
                    {isAdmin && updatingService && (
                        <UpdateServiceModal
                            showUpdateServiceModal={showUpdateServiceModal}
                            setShowUpdateServiceModal={setShowUpdateServiceModal}
                            updatingService={updatingService}
                            setServices={setServices}
                            services={services}
                        />
                    )}
                    {isAdmin && (
                        <OverlayTrigger placement="left" overlay={<Tooltip>Add New Service</Tooltip>}>
                            <Button
                                className="btn-floating btn"
                                style={{ background: 'transparent', borderColor: 'none' }}
                                onClick={() => {
                                    setShowServiceModal(true)
                                }}
                            >
                                <img src={addIcon} alt="add post" width="60" height="60" />
                            </Button>
                        </OverlayTrigger>
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

export default Service
