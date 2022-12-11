import Modal from 'react-bootstrap/Modal'
import Button from 'react-bootstrap/Button'
import Form from 'react-bootstrap/Form'
import { useState } from 'react'
import axios from 'axios'
import { apiUrl } from '../../../contexts/constants'
import swal from 'sweetalert'
const AddIndustryModal = ({ showIndustryModal, setShowIndustryModal }) => {
    // State
    const [newIndustry, setNewIndustry] = useState({
        code: '',
        name: '',
    })
    const [updateLoading, setUpdateLoading] = useState(false)

    const { code, name } = newIndustry

    const onChangeIndustryForm = (event) => setNewIndustry({ ...newIndustry, [event.target.name]: event.target.value })

    const closeDialog = () => {
        setNewIndustry({
            code: '',
            name: '',
        })
        setShowIndustryModal(false)
    }
    const onSubmit = async (event) => {
        event.preventDefault()
        setUpdateLoading(true)
        try {
            const response = await axios.post(`${apiUrl}/admin/field`, newIndustry)
            if (response.data.success) {
                swal('Success', 'Add new Industry success', 'success')
            }
        } catch (error) {
            swal('Error', 'Error when adding Industry !', 'error')
        }
        setUpdateLoading(false)
        closeDialog()
    }
    let body
    body = (
        <Modal className="modal-add-Industry" show={showIndustryModal} onHide={closeDialog}>
            <Modal.Header closeButton>
                <Modal.Title>Add New Industry</Modal.Title>
            </Modal.Header>
            <Form onSubmit={onSubmit}>
                <Modal.Body>
                    <Form.Group className="mb-3">
                        <Form.Text id="title-help" muted>
                            Code
                        </Form.Text>
                        <Form.Control type="text" placeholder="Code" name="code" required aria-describedby="title-help" value={code} onChange={onChangeIndustryForm} />
                    </Form.Group>
                    <Form.Group className="mb-3">
                        <Form.Text id="title-help" muted>
                            Name
                        </Form.Text>
                        <Form.Control type="text" placeholder="Name" name="name" required aria-describedby="title-help" value={name} onChange={onChangeIndustryForm} />
                    </Form.Group>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={closeDialog}>
                        Cancel
                    </Button>
                    <Button variant="primary" type="submit" disabled={updateLoading}>
                        {updateLoading && <span className="spinner-border spinner-border-sm mr-1"></span>}
                        Confirm
                    </Button>
                </Modal.Footer>
            </Form>
        </Modal>
    )

    return body
}
export default AddIndustryModal
