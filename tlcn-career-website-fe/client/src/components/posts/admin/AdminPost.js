import { useContext, useEffect, useState } from 'react'
import { EmployerPostContext } from '../../../contexts/EmployerPostContext'
import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col'
import Spiner from 'react-bootstrap/Spinner'
import Toast from 'react-bootstrap/Toast'
import AdminSinglePost from './AdminSinglePost'
import PostPaging from '../../PostPaging'
import NoPostFound from '../../NoPostFound'
import '../../css/AdminPost.css'
import Dropdown from 'react-bootstrap/Dropdown'
import * as queryString from 'query-string'
import OverlayTrigger from 'react-bootstrap/esm/OverlayTrigger'
import Tooltip from 'react-bootstrap/esm/Tooltip'
import Button from 'react-bootstrap/esm/Button'
import addIcon from '../../../assets/plus-circle-fill.svg'
import AddIndustryModal from './AddIndustryModal'
import AddLocationModal from './AddLocationModal'

const AdminPost = () => {
    const lstStatus = ['ACTIVE', 'WAIT_FOR_PAYMENT', 'WAIT_FOR_ACCEPT', 'DISABLE', 'DELETED', 'DELETED_BY_ADMIN']
    const [postStatus, setPostStatus] = useState('ACTIVE')
    const [keyword, setKeyword] = useState('')
    const [showIndustryModal, setShowIndustryModal] = useState(false)
    const [showLocationModal, setShowLocationModal] = useState(false)
    const {
        postState: { posts, postLoading, currentPage, totalPage },
        getAdminPosts,
        showToast: { show, message, type },
        setShowToast,
        adminDeletePost,
        acceptPost,
    } = useContext(EmployerPostContext)
    const [url, setUrl] = useState(window.location)

    useEffect(() => {
        let params = queryString.parse(window.location.search)

        let reqParams = ''
        if (params && params.page) reqParams += `page=${params.page}&`
        if (params && params.limit) reqParams += `limit=${params.limit}&`
        if (params && params.status) reqParams += `status=${params.status}&`
        if (params && params.keyword) reqParams += `keyword=${params.keyword}&`

        if (params && params.status) setPostStatus(params.status)
        if (params && params.keyword) setKeyword(params.keyword)

        getAdminPosts(reqParams)
    }, [url])

    function setGetParam(params) {
        var newUrl = window.location.origin + window.location.pathname + '?' + params
        window.history.pushState({ path: newUrl }, '', newUrl)
        setUrl(newUrl)
    }

    const handlePageChange = (pageNumber, status, keyword) => {
        const params = new URLSearchParams(window.location.search)

        if (status === undefined) status = postStatus
        if (pageNumber) {
            params.set('page', pageNumber)
            params.set('limit', 9)
        } else {
            params.set('page', 1)
            params.set('limit', 9)
        }
        if (status) params.set('status', status)
        if (status === 'NONE') params.delete('status')
        if (keyword && keyword !== '') params.set('keyword', keyword)
        else params.delete('keyword')
        setGetParam(params.toString())
    }
    const borderColor = {
        ACTIVE: 'success',
        DISABLE: 'secondary',
        WAIT_FOR_ACCEPT: 'warning',
        WAIT_FOR_PAYMENT: 'info',
        DELETED: 'primary',
        DELETED_BY_ADMIN: 'danger',
    }
    let body = null

    if (postLoading) {
        body = (
            <div className="d-flex justify-content-center mt-2">
                <Spiner animation="border" variant="info" />
            </div>
        )
    } else {
        body = (
            <>
                <div className="admin-post-banner">
                    <Dropdown>
                        <Dropdown.Toggle variant={borderColor[postStatus]} id="dropdown-basic">
                            {postStatus}
                        </Dropdown.Toggle>

                        <Dropdown.Menu>
                            <Dropdown.Item
                                key="NONE"
                                onClick={() => {
                                    setPostStatus('NONE')
                                    handlePageChange(1, 'NONE', keyword)
                                }}
                            >
                                NONE
                            </Dropdown.Item>
                            {lstStatus.map((st) => (
                                <Dropdown.Item
                                    key={st}
                                    onClick={() => {
                                        setPostStatus(st)
                                        handlePageChange(1, st, keyword)
                                    }}
                                >
                                    {st}
                                </Dropdown.Item>
                            ))}
                        </Dropdown.Menu>
                    </Dropdown>
                    <div className="admin-post-search-holder">
                        <input
                            id="admin-post-search"
                            className="admin-post-search-input"
                            type="text"
                            value={keyword}
                            onChange={(event) => {
                                setKeyword(event.target.value)
                            }}
                        ></input>
                        <label
                            className="admin-post-lable-serach"
                            htmlFor="admin-post-search"
                            onClick={() => {
                                setKeyword(document.getElementById('admin-post-search').value)
                                handlePageChange(null, null, document.getElementById('admin-post-search').value)
                            }}
                        >
                            Search
                        </label>
                    </div>
                </div>
                {posts.length === 0 ? (
                    <NoPostFound />
                ) : (
                    <>
                        <Row className="row-cols-1 row-cols-md-3 g-4 mx-auto mt-3 container main-row">
                            {posts.map((post) => (
                                <Col key={post.id} className="my-2 ">
                                    <AdminSinglePost post={post} borderColor={borderColor} adminDeletePost={adminDeletePost} acceptPost={acceptPost} />
                                </Col>
                            ))}
                        </Row>
                        <PostPaging handlePageChange={handlePageChange} currentPage={currentPage} totalPage={totalPage} />
                    </>
                )}
            </>
        )
    }

    return (
        <div className="mt-2 container admin-posts-container">
            {body}
            <AddIndustryModal showIndustryModal={showIndustryModal} setShowIndustryModal={setShowIndustryModal} />
            <AddLocationModal showLocationModal={showLocationModal} setShowLocationModal={setShowLocationModal} />

            <OverlayTrigger placement="left" overlay={<Tooltip>Add New Industry</Tooltip>}>
                <Button className="btn-floating btn" style={{ background: 'transparent', borderColor: 'none' }} onClick={setShowIndustryModal.bind(this, true)}>
                    <img src={addIcon} alt="add post" width="60" height="60" />
                </Button>
            </OverlayTrigger>
            <OverlayTrigger placement="left" overlay={<Tooltip>Add New Location</Tooltip>}>
                <Button className="btn-floating btn" style={{ background: 'transparent', borderColor: 'none', marginTop: '100px' }} onClick={setShowLocationModal.bind(this, true)}>
                    <img src={addIcon} alt="add post" width="60" height="60" />
                </Button>
            </OverlayTrigger>
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
    )
}

export default AdminPost
