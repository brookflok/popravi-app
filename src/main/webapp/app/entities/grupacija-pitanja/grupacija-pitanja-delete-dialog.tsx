import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { RouteComponentProps } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';
import { Translate, ICrudGetAction, ICrudDeleteAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IGrupacijaPitanja } from 'app/shared/model/grupacija-pitanja.model';
import { IRootState } from 'app/shared/reducers';
import { getEntity, deleteEntity } from './grupacija-pitanja.reducer';

export interface IGrupacijaPitanjaDeleteDialogProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const GrupacijaPitanjaDeleteDialog = (props: IGrupacijaPitanjaDeleteDialogProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const handleClose = () => {
    props.history.push('/grupacija-pitanja');
  };

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const confirmDelete = () => {
    props.deleteEntity(props.grupacijaPitanjaEntity.id);
  };

  const { grupacijaPitanjaEntity } = props;
  return (
    <Modal isOpen toggle={handleClose}>
      <ModalHeader toggle={handleClose}>
        <Translate contentKey="entity.delete.title">Confirm delete operation</Translate>
      </ModalHeader>
      <ModalBody id="popraviApp.grupacijaPitanja.delete.question">
        <Translate contentKey="popraviApp.grupacijaPitanja.delete.question" interpolate={{ id: grupacijaPitanjaEntity.id }}>
          Are you sure you want to delete this GrupacijaPitanja?
        </Translate>
      </ModalBody>
      <ModalFooter>
        <Button color="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp;
          <Translate contentKey="entity.action.cancel">Cancel</Translate>
        </Button>
        <Button id="jhi-confirm-delete-grupacijaPitanja" color="danger" onClick={confirmDelete}>
          <FontAwesomeIcon icon="trash" />
          &nbsp;
          <Translate contentKey="entity.action.delete">Delete</Translate>
        </Button>
      </ModalFooter>
    </Modal>
  );
};

const mapStateToProps = ({ grupacijaPitanja }: IRootState) => ({
  grupacijaPitanjaEntity: grupacijaPitanja.entity,
  updateSuccess: grupacijaPitanja.updateSuccess,
});

const mapDispatchToProps = { getEntity, deleteEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(GrupacijaPitanjaDeleteDialog);
