import Card from 'react-bootstrap/Card'
import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col'
import { Link } from 'react-router-dom'
const SinglePost = ({ post: { id, title, description, salary, salaryType } }) => (
    <Card className="white-space: nowrap" border="success">
        <Card.Body>
            <Card.Title>
                <Row>
                    <Col className="col-12">
                        <Link className="post-title" to={`/postDetail/${id}`} target="_blank">
                            {title}
                        </Link>
                    </Col>
                </Row>
            </Card.Title>
            <Card.Text className="card-text user-post-description">{description}</Card.Text>
            <Card.Text className="card-text">
                <img
                    src="https://www.pngitem.com/pimgs/m/90-907567_transparent-cash-cow-png-money-icon-png-image.png"
                    width="40px"
                    height="30px"
                    style={{ marginRight: '10px' }}
                    alt="img.png"
                />
                {salary} {salaryType !== 'NONE' ? salaryType : ''}
            </Card.Text>
        </Card.Body>
    </Card>
)
export default SinglePost
