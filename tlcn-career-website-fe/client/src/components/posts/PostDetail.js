import Select from "react-select";
import Button from "react-bootstrap/Button";
import { useParams } from "react-router-dom";
import { useContext, useEffect, useState } from "react";
import { PostContext } from "../../contexts/PostContext";
import Spiner from "react-bootstrap/esm/Spinner";
import Toast from "react-bootstrap/Toast";
import { AuthContext } from "../../contexts/AuthContext";
import peopleIcon from "../../assets/date.png";
import companyIcon from "../../assets/company-icon.png";
import skillIcon from "../../assets/skill-icon.png";
import salaryIcon from "../../assets/salary-icon.png";
import employeeIcon from "../../assets/employee-icon.png";
import AlertMessage from "../layout/AlertMessage";
import NoPostFound from "../NoPostFound";
import Col from "react-bootstrap/esm/Col";
import Row from "react-bootstrap/esm/Row";
import '../css/PostDetail.css'

const PostDetail = () => {
  let { id } = useParams();

  const {
    postState: { posts, postLoading },
    findPostById,
  } = useContext(PostContext);
  const {
    authState: { isUser, user },
    submitUserCV,
  } = useContext(AuthContext);

  useEffect(() => {
    findPostById(id);
  }, [id]);

  const [alert, setAlert] = useState(null);

  const [cvSubmit, setCvSubmit] = useState({
    postId: parseInt(id),
    mediaId: "",
  });

  let listCV = [];

  if (isUser)
    user.profiles.map((profile) =>
      listCV.push({
        label: profile.name,
        value: profile.mediaId,
      })
    );

  const onChangeSelectMedia = (event) =>
    setCvSubmit({
      ...cvSubmit,
      mediaId: event.value,
    });

  const onApplyCV = async (event) => {
    event.preventDefault();
    try {
      const response = await submitUserCV(cvSubmit);
      console.log(response);
      if (response.success) {
        setAlert({ type: "success", message: "Applied successfull" });
        setTimeout(() => setAlert(null), 10000);
      } else {
        setAlert({ type: "danger", message: response.message });
        setTimeout(() => setAlert(null), 10000);
      }
    } catch (error) {
      console.log(error);
    }
  };

  let postHeader, postDetail;
  if (postLoading) {
    postDetail = (
      <div className="d-flex justify-content-center mt-2">
        <Spiner animation="border" variant="info" />
      </div>
    );
  }
  if (posts.length === 0) {
    postDetail = <NoPostFound />;
  } else {
    postHeader = (
      <div className="wrapper-job-detail-header">
        <section className="page-job-detail__header ">
          <div className="box box-md">
            <div className="absolute-right premium-popover-trigger"></div>
            <div className="row">
              <div className="col-lg-9 col-md-9 col-content">
                <div className="job-header-info">
                  <h1 className="job-title">{posts.title}</h1>
                  <div className="row">
                    <div className="col-sm-12 company-name">
                      <span className="name">
                        <a
                          href={"/employer/profile/" + posts.employer.id}
                          title="View Employer Profile !"
                          target="_blank"
                        >
                          {posts.employer.name}
                        </a>
                      </span>
                    </div>
                    <div className="col-sm-12 location-name">
                      <span className="company-location">{posts.location}</span>
                    </div>
                  </div>
                </div>
              </div>
              {isUser ? (
                <div className="col-lg-3 col-md-3 col-btn col-btn-save-section ">
                  <div className="row">
                    <div className="col-xs-6 col-xs-push-6 col-md-12 col-md-push-0">
                      <AlertMessage info={alert} />
                      <Button
                        className="btn btn-primary btn-block btn-apply track-event"
                        variant="warning"
                        onClick={onApplyCV}
                      >
                        Apply now
                      </Button>
                      <Select options={listCV} onChange={onChangeSelectMedia} />
                    </div>
                  </div>
                </div>
              ) : (
                ""
              )}
            </div>
          </div>
        </section>
      </div>
    );

    postDetail = (
      <section className="page-job-detail__detail">
        <div className="tab-content">
          <div
            className="tab-pane tab-pane-job-info box box-md animated fadeIn active"
            id="job-info"
          >
            <div className="row">
              <div className="col-md-3 col-sm-12 tab-sidebar">
                <div className="mobile-box mg-right-10">
                  <div className="box-summary link-list">
                    <div className="row summary-item">
                      <div className="col-xs-2 summary-icon">
                        <img
                          src={peopleIcon}
                          alt="img"
                          className="icon icon-date-posted"
                        />
                      </div>
                      <div className="col-xs-10 summary-content">
                        <span className="content-label"> Expiration Date</span>
                        <span className="content"> {posts.expirationDate}</span>
                      </div>
                    </div>
                    <div className="row summary-item">
                      <div className="col-xs-2 summary-icon">
                        <img
                          src={companyIcon}
                          alt="img"
                          className="icon icon-date-posted"
                        />
                      </div>
                      <div className="col-xs-10 summary-content">
                        <span className="content-label"> Company</span>
                        <span className="content">{posts.employer.name}</span>
                      </div>
                    </div>
                    <div className="row summary-item">
                      <div className="col-xs-2 summary-icon">
                        <img
                          src={skillIcon}
                          alt="img"
                          className="icon icon-date-posted"
                        />
                      </div>
                      <div className="col-xs-10 summary-content">
                        <span className="content-label"> Major</span>
                        <span className="content"> {posts.field}</span>
                      </div>
                    </div>
                    <div className="row summary-item">
                      <div className="col-xs-2 summary-icon">
                        <img
                          src={salaryIcon}
                          alt="img"
                          className="icon icon-date-posted"
                        />
                      </div>
                      <div className="col-xs-10 summary-content">
                        <span className="content-label">
                          {" "}
                          What we can offer
                        </span>
                        <span className="content">
                          {" "}
                          {posts.salary} {posts.salaryType}
                        </span>
                      </div>
                    </div>
                    <div className="row summary-item">
                      <div className="col-xs-2 summary-icon">
                        <img
                          src={employeeIcon}
                          alt="img"
                          className="icon icon-date-posted"
                        />
                      </div>
                      <div className="col-xs-10 summary-content">
                        <span className="content-label"> Recruit</span>
                        <span className="content">
                          {" "}
                          {posts.recruit} Peoples
                        </span>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div className="col-md-9  col-sm-12 tab-main-content">
                <Row>
                  <Col className="col-7">
                    <div className="job-description mobile-box">
                      <h2 style={{ width: "50%" }}>Job Description</h2>
                      <div className="description" style={{ width: "100%" }}>
                        {/* <p>{posts.description}</p> */}
                        {posts.description.split("\n").map((line) => (
                          <p>{line}</p>
                        ))}
                      </div>
                    </div>
                  </Col>
                  <Col className="col-1">

                  </Col>
                  <Col className="col-4">
                    <img src={posts.avatar} alt="" className="img-center mt-5"/>
                  </Col>
                </Row>
              </div>
            </div>
          </div>
        </div>
      </section>
    );
  }

  return (
    <>
      <div id="wrapper" className="main-wrapper">
        <div id="search-widget-wrapper" className="animated">
          <div id="search-widget" className="collapse bg-blue ">
            <div className="search-form container"></div>
            {postHeader}
            {postDetail}
          </div>
        </div>
      </div>
    </>
  );
};

export default PostDetail;
