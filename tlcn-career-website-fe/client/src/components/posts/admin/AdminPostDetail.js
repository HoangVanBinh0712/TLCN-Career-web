import { useParams } from 'react-router-dom'
import { useContext, useEffect, useState } from 'react'
import Spiner from 'react-bootstrap/esm/Spinner'
import peopleIcon from '../../../assets/date.png'
import companyIcon from '../../../assets/company-icon.png'
import skillIcon from '../../../assets/skill-icon.png'
import salaryIcon from '../../../assets/salary-icon.png'
import employeeIcon from '../../../assets/employee-icon.png'
import NoPostFound from '../../NoPostFound'
import Col from 'react-bootstrap/esm/Col'
import Row from 'react-bootstrap/esm/Row'
import { AuthContext } from '../../../contexts/AuthContext'
import { apiUrl } from '../../../contexts/constants'
import axios from 'axios'
import locationIcon from '../../../assets/location.png'
const AdminPostDetail = () => {
    let { id } = useParams()

    const {
        authState: { isAdmin },
    } = useContext(AuthContext)

    const [post, setPost] = useState(null)
    const [postLoading, setPostLoading] = useState(true)

    useEffect(() => {
        async function getAdminPostDetail(id) {
            setPostLoading(true)
            try {
                const response = await axios.get(`${apiUrl}/admin/post/${id}`)
                if (response.data.success) {
                    setPost(response.data.data)
                    return
                }
                setPost(null)
            } catch (error) {
                console.log(error)
                setPost(null)
            }
            setPostLoading(false)
        }
        getAdminPostDetail(id)
    }, [id])

    let postHeader, postDetail
    if (postLoading) {
        postDetail = (
            <div className="d-flex justify-content-center mt-2">
                <Spiner animation="border" variant="info" />
            </div>
        )
    }
    if (post === null || !isAdmin) {
        postDetail = <NoPostFound />
    } else {
        postHeader = (
            <div className="wrapper-job-detail-header">
                <section className="page-job-detail__header ">
                    <div className="box box-md">
                        <div className="absolute-right premium-popover-trigger"></div>
                        <div className="row">
                            <div className="col-lg-9 col-md-9 col-content">
                                <div className="job-header-info">
                                    <h1 className="job-title">{post.title}</h1>
                                    <div className="row">
                                        <div className="col-sm-12 company-name">
                                            <span className="name">
                                                <a href={'/employer/profile/' + post.employer.id} title="View Employer Profile !" target="_blank">
                                                    {post.employer.name}
                                                </a>
                                            </span>
                                        </div>
                                        <div className="col-sm-12 location-name">
                                            <span className="company-location">{post.location}</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
            </div>
        )

        postDetail = (
            <section className="page-job-detail__detail">
                <div className="tab-content">
                    <div className="tab-pane tab-pane-job-info box box-md animated fadeIn active" id="job-info">
                        <div className="row">
                            <div className="col-md-3 col-sm-12 tab-sidebar">
                                <div className="mobile-box mg-right-10">
                                    <div className="box-summary link-list">
                                        <div className="row summary-item">
                                            <div className="col-xs-2 summary-icon">
                                                <img src={peopleIcon} alt="img" className="icon icon-date-posted" />
                                            </div>
                                            <div className="col-xs-10 summary-content">
                                                <span className="content-label"> Expiration Date</span>
                                                <span className="content"> {post.expirationDate}</span>
                                            </div>
                                        </div>
                                        <div className="row summary-item">
                                            <div className="col-xs-2 summary-icon">
                                                <img src={companyIcon} alt="img" className="icon icon-date-posted" />
                                            </div>
                                            <div className="col-xs-10 summary-content">
                                                <span className="content-label"> Company</span>
                                                <span className="content">{post.employer.name}</span>
                                            </div>
                                        </div>
                                        <div className="row summary-item">
                                            <div className="col-xs-2 summary-icon">
                                                <img src={skillIcon} alt="img" className="icon icon-date-posted" />
                                            </div>
                                            <div className="col-xs-10 summary-content">
                                                <span className="content-label"> Major</span>
                                                <span className="content"> {post.field}</span>
                                            </div>
                                        </div>
                                        <div className="row summary-item">
                                            <div className="col-xs-2 summary-icon">
                                                <img src={salaryIcon} alt="img" className="icon icon-date-posted" />
                                            </div>
                                            <div className="col-xs-10 summary-content">
                                                <span className="content-label">What we can offer</span>
                                                <span className="content">
                                                    {post.salary} {post.salaryType}
                                                </span>
                                            </div>
                                        </div>
                                        <div className="row summary-item">
                                            <div className="col-xs-2 summary-icon">
                                                <img src={employeeIcon} alt="img" className="icon icon-date-posted" />
                                            </div>
                                            <div className="col-xs-10 summary-content">
                                                <span className="content-label"> Recruit</span>
                                                <span className="content">{post.recruit} Peoples</span>
                                            </div>
                                            <div className="row summary-item">
                                                <div className="col-xs-2 summary-icon">
                                                    <img src={locationIcon} alt="img" className="icon icon-date-posted" />
                                                </div>
                                                <div className="col-xs-10 summary-content">
                                                    <span className="content-label"> Location</span>
                                                    <span className="content"> {post.city}</span>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div className="col-md-9  col-sm-12 tab-main-content">
                                <Row>
                                    <Col className="col-7">
                                        <div className="job-description mobile-box">
                                            <h2 style={{ width: '50%' }}>Job Description</h2>
                                            <div className="description" style={{ width: '100%' }}>
                                                {/* <p>{post.description}</p> */}
                                                {post.description.split('\n').map((line) => (
                                                    <p>{line}</p>
                                                ))}
                                            </div>
                                        </div>
                                    </Col>
                                    <Col className="col-1"></Col>
                                    <Col className="col-4">
                                        <img src={post.avatar} alt="" className="img-center mt-5" />
                                    </Col>
                                </Row>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        )
    }

    return (
        <>
            <div id="wrapper" className="main-wrapper">
                <div id="search-widget-wrapper" className="animated">
                    <div id="search-widget" className="collapse bg-blue ">
                        <div className="search-form container"></div>
                        {postHeader}
                        {postDetail}
                    </div>
                </div>
            </div>
        </>
    )
}

export default AdminPostDetail
