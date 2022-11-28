import React from 'react'
import SingleResumeLine from './SingleResumeLine'
import { useState, useContext, useEffect } from 'react'
import { AuthContext } from '../../../contexts/AuthContext'
import axios from 'axios'
import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col'
import SinglePostPredict from './SinglePostPredict'
import Toast from 'react-bootstrap/Toast'

export const UserResume = () => {
    const {
        authState: { user },
        deleteCV,
    } = useContext(AuthContext)

    const [listCV, setListCV] = useState(user.profiles)
    const [showToast, setShowToast] = useState({ show: false, message: 'None', type: 'danger' })

    const [currentCvId, setCurrentCvId] = useState(-1)

    const callbackHandlerCv = (cvId) => {
        setCurrentCvId(cvId)
    }

    const handleDeleteCV = async (id) => {
        const res = await deleteCV(id)
        if (res.success) {
            setListCV(listCV.filter((x) => x.mediaId !== id))
            setShowToast({ show: true, message: 'Delete successfully !', type: 'info' })
        } else {
            setShowToast({ show: true, message: res.message, type: 'info' })
        }
    }
    const [listPosts, setlistPosts] = useState({ posts: [] })
    const [listJobs, setlistJobs] = useState({ jobs: [] })
    const [major, setMajor] = useState({ major: '' })

    useEffect(() => {
        if (currentCvId !== -1) {
            const fetchData = async () => {
                const result = await axios(`http://localhost:8081/user/cvpredict?mediaId=${currentCvId}`)
                console.log(result)
                setlistPosts(result.data.data)
                setlistJobs(result.data.jobOptionResponses)
                setMajor(result.data.currentView)
            }

            fetchData()
        }
    }, [currentCvId])

    let body

    if (major.major !== '') {
        body = (
            <div className="MasterLayout_vCol__Raypp MasterLayout_vColLg6__Repj5">
                <div className="vnwBox vnwBoxSmall">
                    <span className="headerTitle">May be you are suitable as a {major}</span>
                </div>

                <div className="Block_Block___z99z ">
                    <div className="ContactInformation_contactInformationComponent__XmtOM">
                        <div className="job-options">
                            <Row className="row-cols-1 row-cols-md-3 g-4 mx-auto mt-3 container main-row post-padding">
                                <Col key={0} className="my-2">
                                    <h5>
                                        {listJobs[0].name}: {listJobs[0].percent}
                                    </h5>
                                    <progress max="100" value={listJobs[0].percent.replace('%', '')}></progress>
                                </Col>
                                <Col key={1} className="my-2 ">
                                    <h5>
                                        {listJobs[1].name}: {listJobs[1].percent}
                                    </h5>
                                    <progress max="100" value={listJobs[1].percent.replace('%', '')}></progress>
                                </Col>
                                <Col key={2} className="my-2 ">
                                    <h5>
                                        {listJobs[2].name}: {listJobs[2].percent}
                                    </h5>
                                    <progress max="100" value={listJobs[2].percent.replace('%', '')}></progress>
                                </Col>
                                <Col key={3} className="my-2 ">
                                    <h5>
                                        {listJobs[3].name}: {listJobs[3].percent}
                                    </h5>
                                    <progress max="100" value={listJobs[3].percent.replace('%', '')}></progress>
                                </Col>
                                <Col key={4} className="my-2 ">
                                    <h5>
                                        {listJobs[4].name}: {listJobs[4].percent}
                                    </h5>
                                    <progress max="100" value={listJobs[4].percent.replace('%', '')}></progress>
                                </Col>
                            </Row>
                            {/* <ul >
                <li>
                  <h5>{listJobs[0].name}:</h5>
                  <progress max="100" value={listJobs[0].percent.replace('%', '')}></progress>
                </li>
                <li>
                  <h5>{listJobs[1].name}:</h5>
                  <progress max="100" value={listJobs[1].percent.replace('%', '')}></progress>
                </li>
                <li>
                  <h5>{listJobs[2].name}:</h5>
                  <progress max="100" value={listJobs[2].percent.replace('%', '')}></progress>
                </li>
                <li>
                  <h5>{listJobs[3].name}:</h5>
                  <progress max="100" value={listJobs[3].percent.replace('%', '')}></progress>
                </li>
                <li>
                  <h5>{listJobs[4].name}:</h5>
                  <progress max="100" value={listJobs[4].percent.replace('%', '')}></progress>
                </li>
              </ul> */}
                        </div>
                        <div>
                            <Row className="row-cols-1  g-4 mx-auto mt-3 container main-row post-padding">
                                {listPosts.map((post) => (
                                    <Col key={post.id} className="my-2 ">
                                        <SinglePostPredict post={post} />
                                    </Col>
                                ))}
                            </Row>
                        </div>
                    </div>
                </div>
            </div>
        )
    } else {
        body = (
            <div className="MasterLayout_vCol__Raypp MasterLayout_vColLg6__Repj5">
                <div className="vnwBox vnwBoxSmall">
                    <span className="headerTitle">No profile has been selected yet</span>
                </div>

                <div className="Block_Block___z99z ">
                    <div className="ContactInformation_contactInformationComponent__XmtOM">
                        <div className="predict-detail">
                            <span className="chose-cv-title"> Select a profile to see suitable jobs</span>
                        </div>
                    </div>
                </div>
            </div>
        )
    }

    return (
        <div className="MasterLayout_vContainer__0VU77 MasterLayout_vBackground__YBFVn">
            <div className="MasterLayout_vRow__eF7VB">
                <div className="MasterLayout_vCol__Raypp MasterLayout_vColLg3__rsAKf" style={{ display: 'block' }}>
                    <div className="vnwBox">
                        <p className="title borderBottom"> Resume Managerment</p>
                        <ul className="sidebarMenu">
                            {listCV.map((media, index) => (
                                <SingleResumeLine key={media.mediaId} media={media} stt={index} handleDeleteCV={handleDeleteCV} handleClickParent={callbackHandlerCv} />
                            ))}
                        </ul>
                    </div>
                </div>
                <Toast
                    show={showToast.show}
                    style={{ position: 'fixed', top: '20%', right: '10px' }}
                    className={`bg-${showToast.type} text-white`}
                    onClose={setShowToast.bind(this, {
                        show: false,
                        message: '',
                        type: null,
                    })}
                    delay={3000}
                    autohide
                >
                    <Toast.Body>
                        <strong>{showToast.message}</strong>
                    </Toast.Body>
                </Toast>
                {body}
            </div>
        </div>
    )
}
