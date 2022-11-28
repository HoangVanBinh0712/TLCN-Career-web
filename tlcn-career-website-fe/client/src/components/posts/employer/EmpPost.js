import { useContext, useEffect, useState } from 'react'
import { EmployerPostContext } from '../../../contexts/EmployerPostContext'
import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col'
import Spiner from 'react-bootstrap/Spinner'
import Button from 'react-bootstrap/esm/Button'
import addIcon from '../../../assets/plus-circle-fill.svg'
import AddPostModal from '../../AddPostsModal'
import OverlayTrigger from 'react-bootstrap/OverlayTrigger'
import Tooltip from 'react-bootstrap/Tooltip'
import Toast from 'react-bootstrap/Toast'
import { MDBRadio, MDBBtnGroup } from 'mdb-react-ui-kit'
import EmployerSinglePost from './EmployerSinglePost'
import CVSubmitModal from '../../CVSubmitModal'
import PostPaging from '../../PostPaging'
import NoPostFound from '../../NoPostFound'
import { apiUrl } from '../../../contexts/constants'
import axios from 'axios'
import UpdatePostModal from '../../UpdatePostsModal'
import '../../css/EmpPost.css'
const EmpPost = () => {
    const postStatuses = ['ACTIVE', 'WAIT_FOR_PAYMENT', 'WAIT_FOR_ACCEPT', 'DISABLE', 'DELETED', 'DELETED_BY_ADMIN']
    const {
        postState: { posts, postLoading, currentPage, totalPage },
        getEmployerPosts,
        setShowAddPostModal,
        setShowCVSubmitModal,
        showToast: { show, message, type },
        setShowToast,
        deletePost,
        cvSubmit,
        setFieldAndCity,
        updatingPost,
        setServices,
    } = useContext(EmployerPostContext)

    const [postStatus, setPostStatus] = useState('ACTIVE')

    const [fcStatus, setfcStatus] = useState('')

    useEffect(() => {
        async function getCityAndField() {
            setfcStatus('loading')

            try {
                const responseField = await axios.get(`${apiUrl}/field?page_number=1&limit=100`)
                const responseCity = await axios.get(`${apiUrl}/city`)

                const services = await axios.get(`${apiUrl}/service`)

                setFieldAndCity([responseField.data.data, responseCity.data.data])
                setServices(services.data.data)

                setfcStatus('success')
            } catch (err) {
                console.log(err)
                setfcStatus('error')

                return null
            }
        }
        getEmployerPosts('page=' + 1 + '&limit=' + 9, postStatuses[0])
        getCityAndField()
    }, [])

    const handlePageChange = (pageNumber, acc) => {
        if (acc === undefined) acc = postStatus
        let key = 'page=' + pageNumber + '&limit=' + 9

        getEmployerPosts(key, acc)
    }

    let body = null

    if (postLoading || fcStatus === 'loading') {
        body = (
            <div className="emp-post-page d-flex justify-content-center mt-2">
                <Spiner animation="border" variant="info" />
            </div>
        )
    }
    // else if (posts.length === 0) {
    //     body = <NoPostFound />
    // }
    else {
        body = (
            <>
                <div className="container emp-post-page">
                    <div className="mt-2 banner">
                        <MDBBtnGroup className="banner-buttons">
                            {postStatuses.map((status) => {
                                return (
                                    <MDBRadio
                                        className="banner-button"
                                        id="btn-radio"
                                        name="options"
                                        wrapperTag="span"
                                        label={status}
                                        checked={postStatus === status}
                                        onChange={() => {
                                            setPostStatus(status)
                                            handlePageChange(1, status)
                                        }}
                                    />
                                )
                            })}
                        </MDBBtnGroup>
                    </div>
                    {posts.length === 0 ? (
                        <NoPostFound />
                    ) : (
                        <>
                            {posts.map((post) => (
                                <Row className="list-posts row-cols-1 row-cols-md-3 g-4 mx-auto main-row">
                                    <EmployerSinglePost post={post} deletePost={deletePost} cvSubmit={cvSubmit} setShowCVSubmitModal={setShowCVSubmitModal} />
                                </Row>
                            ))}
                            <PostPaging className="page-number" handlePageChange={handlePageChange} currentPage={currentPage} totalPage={totalPage} />
                        </>
                    )}

                    {fcStatus === 'success' ? <AddPostModal /> : ''}
                    <CVSubmitModal />
                    <OverlayTrigger placement="left" overlay={<Tooltip>Add New Job</Tooltip>}>
                        <Button className="btn-floating btn" style={{ background: 'transparent', borderColor: 'none' }} onClick={setShowAddPostModal.bind(this, true)}>
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
                    {updatingPost !== null && <UpdatePostModal />}
                </div>
            </>
        )
    }

    return body
}

export default EmpPost
