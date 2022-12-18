import React from 'react';
import { filter } from 'lodash';
import { useState } from 'react';
import PropTypes from 'prop-types';
import { sentenceCase } from 'change-case';
import Label from '../label';

// @mui
import {
  Card,
  Table,
  Paper,
  TableRow,
  TableBody,
  TableCell,
  Container,
  Typography,
  TableContainer,
  TablePagination,
  Tooltip,
  IconButton,
  Stack,
} from '@mui/material';
import DeleteIcon from '@mui/icons-material/Delete';
import ProposalDetailForStudent from './detailModals/ProposalDetailForStudent';

// components
import Scrollbar from './scrollbar';
import DescriptionIcon from '@mui/icons-material/Description';

// sections
import { ProposalPageListHead, ProposalPageListToolbar } from './proposal';
import DeleteModal from '../DeleteModal';

// ----------------------------------------------------------------------

const TABLE_HEAD = [
  { id: 'name', label: 'Proposal Number', alignRight: false },
  { id: 'status', label: 'Status', alignRight: false },
];

// ----------------------------------------------------------------------

function descendingComparator(a, b, orderBy) {
  if (b[orderBy] < a[orderBy]) {
    return -1;
  }
  if (b[orderBy] > a[orderBy]) {
    return 1;
  }
  return 0;
}

function getComparator(order, orderBy) {
  return order === 'desc'
    ? (a, b) => descendingComparator(a, b, orderBy)
    : (a, b) => -descendingComparator(a, b, orderBy);
}

function applySortFilter(array, comparator, query) {
  const stabilizedThis = array.map((el, index) => [el, index]);
  stabilizedThis.sort((a, b) => {
    const order = comparator(a[0], b[0]);
    if (order !== 0) return order;
    return a[1] - b[1];
  });
  if (query) {
    return filter(array, _user => _user.name.toLowerCase().indexOf(query.toLowerCase()) !== -1);
  }
  return stabilizedThis.map(el => el[0]);
}
const ProposalTableForStudent = ({ deleteCourseApprovalRequestRequest, courseRequests }) => {

  const [page, setPage] = useState(0);

  const [order, setOrder] = useState('asc');

  const [orderBy, setOrderBy] = useState('name');

  const [filterName, setFilterName] = useState('');
  
  const [rowsPerPage, setRowsPerPage] = useState(5);

  const [requesDetailsID, setRequesDetailsID] = React.useState(0);

  const handleRequestSort = (event, property) => {
    const isAsc = orderBy === property && order === 'asc';
    setOrder(isAsc ? 'desc' : 'asc');
    setOrderBy(property);
  };

  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = event => {
    setPage(0);
    setRowsPerPage(parseInt(event.target.value, 10));
  };

  const handleFilterByName = event => {
    setPage(0);
    setFilterName(event.target.value);
  };

  const emptyRows = page > 0 ? Math.max(0, (1 + page) * rowsPerPage - courseRequests.length) : 0;

  const filteredUsers = applySortFilter(courseRequests, getComparator(order, orderBy), filterName);

  const isNotFound = !filteredUsers.length && !!filterName;

  const [openDetails, setOpenDetails] = React.useState(false);

  const [openDelete, setOpenDelete] = React.useState(false);

  const [requestType, setRequestType] = React.useState("");

  const handleOpenDetails = id => { 
    setRequesDetailsID(id);
    setOpenDetails(true);
  };

  const handleCloseDetails = () => { 
    setRequesDetailsID(0);
    setOpenDetails(false);
  };

  const handleOpenDelete = id => {
    setRequesDetailsID(id);
    setOpenDelete(true);
  };
  const handleCloseDelete = () => {
    setRequesDetailsID(0);
    setRequestType("");
    setOpenDelete(false);
  };
  
  const handleDelete = () => {
    handleCloseDelete();
    deleteCourseApprovalRequestRequest(requesDetailsID, requestType);
    setRequesDetailsID(0);
  };
  const proposals= [{name:"Proposal 1", status: "WAITING"},{name:"Proposal 2", status:"DECLINED"},{name:"Proposal 3", status:"ACCEPTED"} ];

  return (
    <>
      <Container>
        <Card>
          <ProposalPageListToolbar filterName={filterName} onFilterName={handleFilterByName} />

          <Scrollbar>
            <TableContainer sx={{ minWidth: 800 }}>
              <Table>
                <ProposalPageListHead
                  order={order}
                  orderBy={orderBy}
                  headLabel={TABLE_HEAD}
                  onRequestSort={handleRequestSort}
                />
                <TableBody>
                  {proposals.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage).map(row => {
                    const { id, name,status } = row;

                    return (
                      <TableRow hover key={id} tabIndex={-1} role="checkbox" >
                        <TableCell padding="checkbox"></TableCell>

                        <TableCell component="th" scope="row" padding="none">
                          <Stack direction="row" alignItems="center" spacing={2}>
                            <Typography variant="subtitle2" noWrap>
                              {name}
                            </Typography>
                          </Stack>
                        </TableCell>

                        <TableCell align="center">
                          <Label color={(status === 'WAITING' && 'warning') || (status === 'DECLINED' && 'error') || 'success'}>{sentenceCase(status)}</Label>
                        </TableCell>

                   
                        <TableCell align="right">
                          <Tooltip describeChild title="Open details">
                            <IconButton size="large" color="inherit" onClick={() => handleOpenDetails(id) }>
                              <DescriptionIcon />
                            </IconButton>
                          </Tooltip>
                          { status==="WAITING" ? <><Tooltip describeChild title="Delete">
                            <IconButton size="large" color="error" onClick={() => handleOpenDelete(id) }>
                              <DeleteIcon />
                            </IconButton>
                          </Tooltip> </>: null}
                        </TableCell>
                    <DeleteModal handleDelete={handleDelete} openDelete={openDelete} handleCloseDelete={handleCloseDelete} name={name}/>
                    <ProposalDetailForStudent handleOpenDetails={handleOpenDetails} openDetails={openDetails} handleCloseDetails={handleCloseDetails}/>

                                       </TableRow>
                    );
                  })}
                  {emptyRows > 0 && (
                    <TableRow style={{ height: 53 * emptyRows }}>
                      <TableCell colSpan={6} />
                    </TableRow>
                  )}
                </TableBody>

                {isNotFound && (
                  <TableBody>
                    <TableRow>
                      <TableCell align="center" colSpan={6} sx={{ py: 3 }}>
                        <Paper
                          sx={{
                            textAlign: 'center',
                          }}
                        >
                          <Typography variant="h6" paragraph>
                            Not found
                          </Typography>

                          <Typography variant="body2">
                            No results found for &nbsp;
                            <strong>&quot;{filterName}&quot;</strong>.
                            <br /> Try checking for typos or using complete words.
                          </Typography>
                        </Paper>
                      </TableCell>
                    </TableRow>
                  </TableBody>
                )}
              </Table>
            </TableContainer>
          </Scrollbar>

          <TablePagination
            rowsPerPageOptions={[5, 10, 25]}
            component="div"
            count={courseRequests.length}
            rowsPerPage={rowsPerPage}
            page={page}
            onPageChange={handleChangePage}
            onRowsPerPageChange={handleChangeRowsPerPage}
          />
        </Card>
      </Container>
    </>
  );
};

ProposalTableForStudent.propTypes = {
    courseRequests: PropTypes.array,
    deleteCourseApprovalRequestRequest: PropTypes.func
};
  
ProposalTableForStudent.defaultProps = {
    courseRequests: [],
};


export default ProposalTableForStudent;
