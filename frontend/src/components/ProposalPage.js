import { Grid, Stack, Typography } from '@mui/material';
import React from 'react';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';

import ProposalTable from './table/ProposalTable';

const ProposalPage = () => {

    return (
        <Stack spacing={2}>
            <Typography gutterBottom variant="h1" textAlign={ "center" } component="div">
                Course Proposals
            </Typography>
            <Grid container justifyContent={'center'}>
                <Grid item xs={12}>
                    <ProposalTable />
                </Grid>
            </Grid>
        </Stack>
    );
};
const mapStateToProps = state => {
    const courseRequests = state.requests.courseRequests;
    const userId = state.user.user.id;
    const typeForReq = state.auth.authTypeForReq;
    return {
        courseRequests,
        userId,
        typeForReq,
    };
};



ProposalPage.propTypes = {
    courseRequests: PropTypes.array,
    userId: PropTypes.number,
    getCourseApprovalRequestsRequest: PropTypes.func,
    deleteCourseApprovalRequestRequest: PropTypes.func,
    typeForReq: PropTypes.string,
};
  
ProposalPage.defaultProps = {
    courseRequests: [],
};

export default connect(mapStateToProps)(ProposalPage);