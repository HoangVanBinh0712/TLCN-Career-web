import Card from 'react-bootstrap/Card'
import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col'
import Button from 'react-bootstrap/Button'
import { Link } from 'react-router-dom'
import { useContext, useState } from 'react'
import { EmployerPostContext } from '../../../contexts/EmployerPostContext'

const EmployerSinglePost = ({ post, deletePost, cvSubmit, setShowCVSubmitModal }) => {
    const { setUpdatingPost, setShowUpdatePost } = useContext(EmployerPostContext)
    const [listCVLoading, setListCVLoading] = useState(false)
    const [deleteLoading, setDeleteLoading] = useState(false)

    return (
        <Card className="white-space: nowrap card-emp-post" border="success">
            <Card.Body>
                <Row>
                    <Col className="col-10">
                        <Card.Title>
                            <Link className="post-title" to={`postDetail/${post.id}`} target="_blank">
                                {post.title}
                            </Link>
                        </Card.Title>
                        <Card.Text>
                            <label for="post-description">
                                <strong>Description </strong>
                            </label>
                            <p id="post-description">{post.description}</p>
                        </Card.Text>
                        <Card.Text>
                            <img
                                src="https://www.pngitem.com/pimgs/m/90-907567_transparent-cash-cow-png-money-icon-png-image.png"
                                width="40px"
                                height="30px"
                                style={{ marginRight: '10px' }}
                                alt="img.png"
                            />
                            {post.salary} {post.salaryType !== 'NONE' ? post.salaryType : ''}
                        </Card.Text>
                    </Col>
                    <Col className="col-1"></Col>
                    <Col className="col-1">
                        <div className="group-buttons">
                            <Button
                                disabled={listCVLoading}
                                className="btn emp-post-btn"
                                variant="outline-info"
                                onClick={async () => {
                                    //Load data then show modal
                                    setListCVLoading(true)

                                    const listCV = (await cvSubmit(post.id)).data
                                    setShowCVSubmitModal({
                                        show: true,
                                        listCV: listCV === undefined ? [] : listCV,
                                    })
                                    setListCVLoading(false)
                                }}
                            >
                                {listCVLoading && <span className="spinner-border spinner-border-sm mr-1"></span>}
                                CV
                            </Button>

                            <Button
                                className="btn emp-post-btn"
                                variant="outline-warning"
                                onClick={() => {
                                    setUpdatingPost(post)
                                    setShowUpdatePost(true)
                                }}
                            >
                                Update
                            </Button>

                            <Button
                                disabled={deleteLoading}
                                className="btn emp-post-btn"
                                variant="outline-danger"
                                onClick={async () => {
                                    setDeleteLoading(true)
                                    await deletePost(post.id)
                                    setDeleteLoading(false)
                                }}
                            >
                                {deleteLoading && <span className="spinner-border spinner-border-sm mr-1"></span>}
                                Delete
                            </Button>
                        </div>
                    </Col>
                </Row>
            </Card.Body>
        </Card>
    )
}
export default EmployerSinglePost
