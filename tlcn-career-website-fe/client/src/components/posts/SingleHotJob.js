import Card from 'react-bootstrap/Card'
import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col'
import { Link } from 'react-router-dom'
import {lib} from '../../contexts/constants'

const SingleHotJob = ({ post: { id, title, description, salary, salaryType } }) => {
    return (
        <Card
            className="white-space: nowrap hot-job-border-post"
            onClick={() => {
                const win = window.open(`/postDetail/${id}`, '_blank')
                win.focus()
            }}
        >
            <Card.Body>
                <Card.Title>
                    <Row>
                        <Col className="col-12">
                            <Link className="hot-job-title" to="#">
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
                    {salary}
                    {salaryType !== 'NONE' ? lib[salaryType] : ''}
                </Card.Text>
            </Card.Body>
        </Card>
    )
}
export default SingleHotJob
