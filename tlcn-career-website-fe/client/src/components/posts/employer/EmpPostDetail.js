import { useParams } from 'react-router-dom'
import { useContext, useEffect, useState } from 'react'
import Spiner from 'react-bootstrap/esm/Spinner'
import peopleIcon from '../../../assets/date.png'
import companyIcon from '../../../assets/company-icon.png'
import skillIcon from '../../../assets/skill-icon.png'
import salaryIcon from '../../../assets/salary-icon.png'
import employeeIcon from '../../../assets/employee-icon.png'
import resumeIcon from '../../../assets/resume-icon.png'
import locationIcon from '../../../assets/location.png'
import NoPostFound from '../../NoPostFound'
import { EmployerPostContext } from '../../../contexts/EmployerPostContext'
import { AuthContext } from '../../../contexts/AuthContext'
import { apiUrl } from '../../../contexts/constants'
import axios from 'axios'
import CVSubmitModal from '../../CVSubmitModal'
import '../../css/PostDetail.css'
import Row from 'react-bootstrap/esm/Row'
import Col from 'react-bootstrap/esm/Col'
import {lib} from '../../../contexts/constants'

const EmpPostDetail = () => {
    let { id } = useParams()

    const { cvSubmit, setShowCVSubmitModal } = useContext(EmployerPostContext)

    const {
        authState: { isEmployer, user },
    } = useContext(AuthContext)

    const [post, setPost] = useState(null)
    const [postLoading, setPostLoading] = useState(true)

    const [listCVLoading, setListCVLoading] = useState(false)

    useEffect(() => {
        async function getEmployerPostDetail(id) {
            setPostLoading(true)
            try {
                const response = await axios.get(`${apiUrl}/employer/post/${id}`)
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
        getEmployerPostDetail(id)
    }, [id])
    console.log(post)

    let postHeader, postDetail
    if (postLoading) {
        postDetail = (
            <div className="d-flex justify-content-center mt-2">
                <Spiner animation="border" variant="info" />
            </div>
        )
    }
    if (post === null || !isEmployer || user.id !== post.employer.id) {
        postDetail = <NoPostFound />
    } else {
        postHeader = (
            <div className="wrapper-job-detail-header">
                <section className="page-job-detail__header ">
                    <div className="box box-md">
                        <div className="absolute-right premium-popover-trigger"></div>
                        <div className="job-header-info">
                            <h1 className="job-title">{post.title}</h1>
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
                                <div className="mobile-box mg-right-10p">
                                    <div className="box-summary link-list">
                                        <div className="row summary-item">
                                            <div className="col-xs-2 summary-icon">
                                                <img src={locationIcon} alt="img" className="icon icon-date-posted" />
                                            </div>
                                            <div className="col-xs-10 summary-content">
                                                <span className="content-label"> Location</span>
                                                <span className="content"> {post.location}</span>
                                            </div>
                                        </div>
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
                                                    {post.salary}{lib[post.salaryType]}
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
                                        <div className="cvSubmitted">
                                            <div className="row summary-item gradient-border " disabled={listCVLoading}>
                                                <div className="col-xs-2 summary-icon">
                                                    <img src={resumeIcon} alt="img" className="icon icon-date-posted" />
                                                </div>
                                                <div
                                                    className="col-xs-10 summary-content"
                                                    onClick={async () => {
                                                        setListCVLoading(true)
                                                        const response = await cvSubmit(post.id)
                                                        const listCV = response.data
                                                        setShowCVSubmitModal({
                                                            show: true,
                                                            listCV: listCV === undefined ? [] : listCV,
                                                        })
                                                        setListCVLoading(false)
                                                    }}
                                                >
                                                    <span className="content-label"> View RESÚME</span>
                                                    {listCVLoading && <span className="content-label spinner-border spinner-border-sm mr-1"></span>}
                                                </div>
                                                <CVSubmitModal />
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            {/* <div className="col-md-9  col-sm-12 tab-main-content">
                                <div className="job-description mobile-box">
                                    <h2 style={{ width: '50%' }}>Job Description</h2>
                                    <ul className="description">
                                        {post.description.split('\n').map((line) => (
                                            <li>{line}</li>
                                        ))}
                                    </ul>
                                </div>
                                {post.avatar ? (
                                    <div>
                                        <h2 style={{ width: '50%' }}>Image</h2>
                                        <img src={post.avatar} alt="" className="img-center mb-5" />
                                    </div>
                                ) : (
                                    ''
                                )}
                            </div> */}
                            <div className="col-md-9  col-sm-12 tab-main-content">
                                <Row>
                                    <Col className="col-7">
                                        <div className="job-description mobile-box">
                                            <h2 style={{ width: '50%' }}>Job Description</h2>
                                            <div className="description" style={{ width: '100%' }}>
                                                {/* <p>{posts.description}</p> */}
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

export default EmpPostDetail
