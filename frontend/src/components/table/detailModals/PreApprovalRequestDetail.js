import React, { useState } from "react";
import { Button, Grid, Modal, TextField, Typography } from "@mui/material";
import { Box, Stack } from "@mui/system";
import PropTypes  from 'prop-types';
import { MDBCard, MDBCardBody, MDBCardText, MDBCol, MDBContainer, MDBRow } from "mdb-react-ui-kit";
import { sentenceCase } from 'change-case';

import Label from '../../label';
import { connect } from "react-redux";
import CourseCard from "./CourseCard";
import MobilityCourseCard from "./MobilityCourseCard";


const PreApprovalRequestDetail = ({ openDetails, handleCloseDetails, authType, preApprovalForm }) => {

    const [feedback, setFeedback] = useState('');

    const handleChangeFeedback = e => setFeedback(e.target.value);

    console.log(preApprovalForm);

    const status = preApprovalForm.status;

    return (
        <Modal
            open={openDetails}
            onClose={handleCloseDetails}
            aria-labelledby="modal-modal-title"
            aria-describedby="modal-modal-description"
            BackdropProps={{ style: { backgroundColor: "rgba(0, 0, 0, 0.2)" } }}
        >
            <Box sx={style}>
                <Stack spacing={6}>
                    <Typography id="modal-modal-title" textAlign={"center"}
                        variant="h2" component="h1">
                        Pre-Approval Form
                    </Typography>
                    <Stack alignItems={"center"} spacing={3}>
                        <section style={{ width: '100%', backgroundColor: '#eee' }}>
                            <MDBContainer className="py-5">
                                {preApprovalForm?.mobilityCourses?.map((mobilityCourse, index) => (
                                    <MobilityCourseCard key={index} mobilityCourse={mobilityCourse} mobilityIndex={index} />
                                ))}

                                <Typography id="modal-modal-title" sx={{ marginBottom: '10px'}}
                                    variant="h3" component="h1">
                                    Response
                                </Typography>
                                {(status == 'waiting') && (authType == 'Course Coordinator') 
                                ? (
                                    <MDBRow>
                                        <MDBCol md="12">
                                            <MDBCard className="mb-4 mb-md-0">
                                            <MDBCardBody>
                                                <MDBRow>
                                                    <MDBCol sm="3">
                                                        <MDBCardText>Status</MDBCardText>
                                                    </MDBCol>
                                                    <MDBCol sm="9">
                                                        <MDBCardText className="text-muted">
                                                            <Label color={(status === 'waiting' && 'warning') || (status === 'rejected' && 'error') || 'success'}>{sentenceCase(status)}</Label>
                                                        </MDBCardText>
                                                    </MDBCol>
                                                </MDBRow>
                                                <hr />
                                                <MDBRow>
                                                    <MDBCol sm="3">
                                                        <MDBCardText>Feedback</MDBCardText>
                                                    </MDBCol>
                                                    <MDBCol sm="9">
                                                        <TextField
                                                            id="outlined-multiline-flexible"
                                                            multiline
                                                            fullWidth
                                                            maxRows={4}
                                                            value={feedback}
                                                            onChange={handleChangeFeedback}
                                                        />
                                                    </MDBCol>
                                                </MDBRow>
                                                <hr />
                                                <Grid container >
                                                    
                                                    <Grid item xs={2} >
                                                        <Button sx={{margin: 'auto'}} variant="contained" color="success" size="medium" onClick={handleCloseDetails} >
                                                            Accept
                                                        </Button>
                                                    </Grid>
                                                    
                                                    <Grid item xs={2} >
                                                        <Button sx={{margin: 'auto'}} variant="contained" color="error" size="medium" onClick={handleCloseDetails} >
                                                            Reject
                                                        </Button>
                                                    </Grid>
                                                </Grid>
                                            </MDBCardBody>
                                            </MDBCard>
                                        </MDBCol>
                                    </MDBRow>
                                )
                                
                                : (
                                    <MDBRow>
                                        <MDBCol md="12">
                                            <MDBCard className="mb-4 mb-md-0">
                                            <MDBCardBody>
                                                { (authType !== 'Course Coordinator') 
                                                ? (
                                                    <MDBRow>
                                                        <MDBCol sm="3">
                                                            <MDBCardText>Department Coordiantor</MDBCardText>
                                                        </MDBCol>
                                                        <MDBCol sm="9">
                                                            <MDBCardText className="text-muted">
                                                                {preApprovalForm.departmentCoordinator}
                                                            </MDBCardText>
                                                        </MDBCol>
                                                    </MDBRow>
                                                ) : null }
                                                { (authType !== 'Course Coordinator') ? <hr /> : null }
                                                <MDBRow>
                                                    <MDBCol sm="3">
                                                        <MDBCardText>Status</MDBCardText>
                                                    </MDBCol>
                                                    <MDBCol sm="9">
                                                        <MDBCardText className="text-muted">
                                                            <Label color={(status === 'waiting' && 'warning') || (status === 'rejected' && 'error') || 'success'}>{sentenceCase(status)}</Label>
                                                        </MDBCardText>
                                                    </MDBCol>
                                                </MDBRow>
                                                {status !== 'waiting' ? <hr /> : null}
                                                {status !== 'waiting' ? (<MDBRow>
                                                    <MDBCol sm="3">
                                                        <MDBCardText>Feedback</MDBCardText>
                                                    </MDBCol>
                                                    <MDBCol sm="9">
                                                        <MDBCardText className="text-muted">{preApprovalForm.feedback}</MDBCardText>
                                                    </MDBCol>
                                                </MDBRow>): null}
                                            </MDBCardBody>
                                            </MDBCard>
                                        </MDBCol>
                                    </MDBRow>
                                )

                                }
                                 
                            </MDBContainer>
                            </section>
                    </Stack>
                    <Grid container sx={{marginTop: '0px'}} alignItems={"flex-end"}>
                        <Button sx={{margin: 'auto'}} variant="contained" color="error" size="medium" onClick={handleCloseDetails} >
                            Close
                        </Button>
                    </Grid>
                </Stack>
            </Box>
        </Modal>
    );
};

const style = {
    position: "absolute",
    top: "50%",
    left: "50%",
    transform: "translate(-50%, -50%)",
    width: '60%',
    bgcolor: "background.paper",
    border: "none",
    borderRadius: "6px",
    boxShadow: 24,
    p: 4,
    maxHeight: "90%",
    mb: 2,
    display: "flex",
    flexDirection: "column",
    overflow: "hidden",
    overflowY: "scroll",
};


const mapStateToProps = state => {
    const authType = state.auth.authType;
    return {
        authType,
    };
};

PreApprovalRequestDetail.propTypes = {
    openDetails: PropTypes.bool,
    handleCloseDetails: PropTypes.func,
    authType: PropTypes.string,
    id: PropTypes.number,
    preApprovalForm: PropTypes.object,
};
  
PreApprovalRequestDetail.defaultProps = {
    openDetails: false,
    handleCloseDetails: f => f,
    preApprovalForm: {},
};

export default connect(mapStateToProps, {})(PreApprovalRequestDetail);
