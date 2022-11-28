import Card from 'react-bootstrap/Card'
import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col'
import Button from 'react-bootstrap/Button'
import { Link } from 'react-router-dom'
import { useState } from 'react'
import '../../css/AdminSinglePost.css'

const SinglePost = ({ post: { id, title, description, salary, salaryType, adminAceptedEmail, status }, adminDeletePost, acceptPost, borderColor }) => {
    const [functionLoading, setFunctionLoading] = useState(false)

    return (
        <Card className="white-space: nowrap" border={borderColor[status]}>
            <Card.Body>
                <Card.Title>
                    <Row>
                        <Col className="col-12">
                            <Link className="post-title" to={`postDetail/${id}`} target="_blank">
                                {title}
                            </Link>
                        </Col>
                    </Row>
                </Card.Title>
                <Card.Text className="card-text admin-post-descriptions">{description}</Card.Text>
                <Card.Text className="card-text">
                    <img
                        src="https://www.pngitem.com/pimgs/m/90-907567_transparent-cash-cow-png-money-icon-png-image.png"
                        width="40px"
                        height="30px"
                        style={{ marginRight: '10px' }}
                        alt=""
                    />
                    {salary} {salaryType !== 'NONE' ? salaryType : ''}
                </Card.Text>
                <Row className="admin-single-post-button">
                    {status === 'WAIT_FOR_ACCEPT' || status === 'DELETED_BY_ADMIN' ? (
                        <>
                            <Button
                                disabled={functionLoading}
                                className="admin-post-btn"
                                variant="outline-warning"
                                onClick={async () => {
                                    setFunctionLoading(true)
                                    await acceptPost(id)
                                    setFunctionLoading(false)
                                }}
                            >
                                {functionLoading && <span className="spinner-border spinner-border-sm mr-1"></span>} Accept
                            </Button>
                        </>
                    ) : (
                        ''
                    )}
                    {status !== 'DELETED_BY_ADMIN' ? (
                        <Button
                            disabled={functionLoading}
                            className="admin-post-btn"
                            variant="outline-danger"
                            onClick={async () => {
                                setFunctionLoading(true)
                                await adminDeletePost(id)
                                setFunctionLoading(false)
                            }}
                        >
                            {functionLoading && <span className="spinner-border spinner-border-sm mr-1"></span>} Force Delete
                        </Button>
                    ) : (
                        ''
                    )}
                </Row>
            </Card.Body>
        </Card>
    )
}
export default SinglePost
