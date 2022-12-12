import Card from 'react-bootstrap/Card'
import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col'
import { Link } from 'react-router-dom'
import locationIcon from '../../assets/location.png'
import moneyIcon from '../../assets/moneyIcon.png'
import {lib} from '../../contexts/constants'
const SinglePost = ({ post: { id, title, description, salary, salaryType, city } }) => {
    return (
        <Card className="white-space: nowrap" border={'success'}>
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
                    <Row>
                        <Col className="col-6">
                            <img src={moneyIcon} width="40px" height="30px" style={{ marginRight: '10px' }} alt="img.png" />
                            {salary}
                            {salaryType !== 'NONE' ? lib[salaryType] : ''}
                        </Col>

                        <Col className="col-6">
                            <img src={locationIcon} width="40px" height="30px" style={{ marginRight: '10px' }} alt="img.png" />
                            {city.name}
                        </Col>
                    </Row>
                </Card.Text>
            </Card.Body>
        </Card>
    )
}
export default SinglePost
