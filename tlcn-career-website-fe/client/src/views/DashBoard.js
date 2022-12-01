import { PostContext } from '../contexts/PostContext'
import { useContext, useEffect, useState } from 'react'
import Spiner from 'react-bootstrap/Spinner'
import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col'
import SinglePost from '../components/posts/SinglePost'
import mainimage from '../assets/banner-top.png'
import iconsearch from '../assets/search-icon.png'
import Button from 'react-bootstrap/Button'
import Form from 'react-bootstrap/Form'
import { apiUrl } from '../contexts/constants'
import '../components/css/Pager.css'
import PostPaging from '../components/PostPaging'
import NoPostFound from '../components/NoPostFound'
import axios from 'axios'
import { DEFAULT_PAGE_POSTS } from '../contexts/constants'
import * as queryString from 'query-string'
import '../components/css/UserPost.css'
import SingleHotJob from '../components/posts/SingleHotJob'

const DashBoard = () => {
    let params = queryString.parse(window.location.search)

    const {
        postState: { posts, postLoading, currentPage, totalPage },
        getPosts,
    } = useContext(PostContext)
    //const {authState:{isUser, isEmployer}} =useContext(AuthContext)
    const [searchForm, setSearchForm] = useState({
        title: '',
        field: '',
        city: '',
    })
    const [hotJob, setHotJob] = useState([])
    const [url, setUrl] = useState(window.location)

    const [changeLimit, setChangeLimit] = useState(DEFAULT_PAGE_POSTS)

    const { title, field, city } = searchForm

    const onChangeSearchForm = (event) => setSearchForm({ ...searchForm, [event.target.name]: event.target.value })

    const [fields, setFields] = useState([])
    const [cities, setCities] = useState([])

    const handlePageChange = (pageNumber) => {
        let key = 'page=' + pageNumber + '&limit=' + changeLimit + '&'

        if (title) key += `keyword=${title}&`
        if (field) key += `fieldId=${field}&`
        if (city) key += `cityId=${city}`
        setGetParam(key)

        //getPosts(key);
    }

    const reloadPage = (input) => {
        let key = 'page=' + 1 + '&limit=' + input

        setGetParam(key)
        //getPosts(key);
    }

    useEffect(() => {
        const getFields = async () => {
            const response = await axios.get(`${apiUrl}/field?page_number=1&limit=100`)
            if (response.data.success) {
                setFields(response.data.data)
            }
        }
        const getCities = async () => {
            const response = await axios.get(`${apiUrl}/city`)
            if (response.data.success) {
                setCities(response.data.data)
            }
        }
        const getHotJob = async () => {
            const page = Math.round(Math.random(20) * 10)
            const response = await axios.get(`${apiUrl}/post/hot-job?page=${page}&limit=9`)
            if (response.data.success) {
                setHotJob(response.data.data)
            }
        }
        const intervalId = setInterval(() => {
            getHotJob()
            //Need to move element
        }, 10000)

        if (fields.length === 0) getFields()
        if (cities.length === 0) getCities()
        if (hotJob.length === 0) getHotJob()
        let reqParams = ''
        if (params && params.keyword) reqParams += `keyword=${params.keyword}&`
        if (params && params.page) reqParams += `page=${params.page}&`
        if (params && params.limit) reqParams += `limit=${params.limit}&`
        if (params && params.fieldId) reqParams += `fieldId=${params.fieldId}&`
        if (params && params.cityId) reqParams += `cityId=${params.cityId}&`

        console.log(reqParams)
        getPosts(reqParams === '' ? 'page=1&limit=12' : reqParams)
        return () => clearInterval(intervalId)
    }, [url])
    function setGetParam(params) {
        var newUrl = window.location.origin + window.location.pathname + '?' + params
        window.history.pushState({ path: newUrl }, '', newUrl)
        setUrl(newUrl)
    }
    let body = null

    if (postLoading) {
        body = (
            <div className="d-flex justify-content-center" style={{ marginTop: '10%' }}>
                <Spiner animation="border" variant="info" />
            </div>
        )
    }
    if (posts === null || posts.length === 0) {
        body = <NoPostFound />
    } else {
        body = (
            <>
                {posts.length === 0 ? (
                    <NoPostFound />
                ) : (
                    <>
                        <div className="hot-post-container">
                            <h3 className="hot-job-text">Hot job</h3>

                            <Row className="row-cols-1 row-cols-md-3 g-4 mx-auto mt-3 main-row post-padding hot-job-container">
                                {hotJob.map((post) => (
                                    <Col key={post.id} style={{ padding: '10px', marginTop: '0px' }}>
                                        <SingleHotJob post={post} />
                                    </Col>
                                ))}
                            </Row>
                        </div>

                        <hr></hr>

                        <div className="normal-post-container">
                            <h3 className="hot-job-text">Available job</h3>

                            <Row className="row-cols-1 row-cols-md-3 g-4 mx-auto mt-3 main-row post-padding">
                                {posts.map((post) => (
                                    <Col key={post.id} style={{ padding: '10px', marginTop: '0px' }}>
                                        <SinglePost post={post} />
                                    </Col>
                                ))}
                            </Row>
                            <PostPaging handlePageChange={handlePageChange} currentPage={currentPage} totalPage={totalPage} />
                            <label forhtml="limitSelect" className="page-size-label">
                                Change Limit Page Size:
                            </label>
                            <select
                                className="page-size-select"
                                id="limitSelect"
                                onChange={(e) => {
                                    setChangeLimit(e.target.value)
                                    reloadPage(e.target.value)
                                }}
                            >
                                <option value={DEFAULT_PAGE_POSTS} defaultChecked>
                                    {DEFAULT_PAGE_POSTS}
                                </option>
                                <option value={DEFAULT_PAGE_POSTS * 2}>{DEFAULT_PAGE_POSTS * 2}</option>
                                <option value={DEFAULT_PAGE_POSTS * 4}>{DEFAULT_PAGE_POSTS * 4}</option>
                            </select>
                        </div>
                    </>
                )}
            </>
        )
    }

    return (
        <>
            <div className="img-main">
                <img src={mainimage} style={{ width: '100%', height: '300px', padding: '0 0 0 0 ' }} alt="banner-img" />
                <Form className="form-tim-kiem">
                    <Row className="format-row">
                        <Col className="col-5">
                            <Form.Control type="text" placeholder="Input your key word" name="title" value={title} onChange={onChangeSearchForm} />
                        </Col>
                        <Col className="col-4">
                            <select className="select-form-search" name="city" onChange={onChangeSearchForm}>
                                <option key={''} value="" defaultChecked>
                                    Select City Location
                                </option>
                                {cities.map((city) => (
                                    <option key={city.id} value={city.id}>
                                        {city.name}
                                    </option>
                                ))}
                            </select>
                        </Col>
                        <Col className="col-3">
                            <Button
                                className="btn-search-form"
                                variant="primary"
                                onClick={() => {
                                    let key = 'page=' + 1 + '&limit=' + changeLimit + '&'
                                    if (title) key += `keyword=${title}&`
                                    if (field) key += `fieldId=${field}&`
                                    if (city) key += `cityId=${city}`
                                    setGetParam(key)

                                    //                  getPosts(key);
                                }}
                            >
                                <img src={iconsearch} alt="img" width="26" height="26" className="mr-2" />
                                Search
                            </Button>
                        </Col>
                    </Row>
                    <Row className="format-row">
                        <Col className="col-5">
                            <select className="select-form-search" name="field" onChange={onChangeSearchForm}>
                                <option value="" defaultChecked>
                                    Select Field
                                </option>
                                {fields.map((field) => (
                                    <option key={field.id} value={field.id}>
                                        {field.name}
                                    </option>
                                ))}
                            </select>
                        </Col>
                    </Row>
                </Form>
            </div>
            <div className="user-post-container">{body}</div>
        </>
    )
}

export default DashBoard
