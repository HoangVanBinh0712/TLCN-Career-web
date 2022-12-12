import Card from 'react-bootstrap/Card'
import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col'
import { Link } from 'react-router-dom'
import SalaryIcon from '../../../assets/salary-post-icon.png'
import Button from 'react-bootstrap/esm/Button'
import {lib} from '../../../contexts/constants'

const SinglePostPredict = ({ post: { id, title, description, salary, salaryType } }) => (
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
            <Card.Text className="card-text">{description}</Card.Text>
            <Card.Text className="card-text">
                <Link to={`/postDetail/${id}`} target="_blank">
                    <Button>View Post</Button>
                </Link>
                <img className="flex-right" src={SalaryIcon} width="40px" height="30px" style={{ marginRight: '10px' }} alt="img.png" />
                {salary}
                {salaryType !== 'NONE' ? lib[salaryType] : ''}
            </Card.Text>
        </Card.Body>
    </Card>
)
export default SinglePostPredict
