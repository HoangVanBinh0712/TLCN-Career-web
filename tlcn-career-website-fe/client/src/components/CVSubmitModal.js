import Modal from "react-bootstrap/Modal";
import Table from "react-bootstrap/Table";
import { useContext } from "react";
import { EmployerPostContext } from "../contexts/EmployerPostContext";

const CVSubmitModal = () => {
  const { showCVSubmitModal, setShowCVSubmitModal } =
    useContext(EmployerPostContext);
  let i = 0;
  function formatDateToDDMMYYYY (input){
    const date = new Date(input)
    return (date.getDate()) + "/" + (date.getMonth() + 1) + "/" +(date.getFullYear())
  }
  return (
    <Modal
      show={showCVSubmitModal.show}
      onHide={() => {
        setShowCVSubmitModal({ show: false, listCV: [] });
      }}
    >
      <Modal.Header closeButton>
        <Modal.Title>List Submitted CV</Modal.Title>
      </Modal.Header>
      <Table striped bordered hover>
        <thead>
          <tr>
            <th>#</th>
            <th>Date</th>
            <th>Url</th>
            <th>Match</th>
          </tr>
        </thead>
        <tbody>
          {showCVSubmitModal.listCV.map((cv) => {
            i++;
            
            return (
              <>
                <tr>
                  <td>{i}</td>
                  <td>{formatDateToDDMMYYYY(cv.date)}</td>
                  <td><a href={cv.url} target="_blank">View CV</a></td>
                  <td>{cv.matchPercent}%</td>
                </tr>
              </>
            );
          })}
        </tbody>
      </Table>
    </Modal>
  );
};

export default CVSubmitModal;
