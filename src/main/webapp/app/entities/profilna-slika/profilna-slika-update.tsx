import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IDodatniInfoUser } from 'app/shared/model/dodatni-info-user.model';
import { getEntities as getDodatniInfoUsers } from 'app/entities/dodatni-info-user/dodatni-info-user.reducer';
import { getEntity, updateEntity, createEntity, reset } from './profilna-slika.reducer';
import { IProfilnaSlika } from 'app/shared/model/profilna-slika.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IProfilnaSlikaUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ProfilnaSlikaUpdate = (props: IProfilnaSlikaUpdateProps) => {
  const [dodatniInfoUserId, setDodatniInfoUserId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { profilnaSlikaEntity, dodatniInfoUsers, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/profilna-slika');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getDodatniInfoUsers();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.created = convertDateTimeToServer(values.created);

    if (errors.length === 0) {
      const entity = {
        ...profilnaSlikaEntity,
        ...values,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="popraviApp.profilnaSlika.home.createOrEditLabel">
            <Translate contentKey="popraviApp.profilnaSlika.home.createOrEditLabel">Create or edit a ProfilnaSlika</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : profilnaSlikaEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="profilna-slika-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="profilna-slika-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="titleLabel" for="profilna-slika-title">
                  <Translate contentKey="popraviApp.profilnaSlika.title">Title</Translate>
                </Label>
                <AvField id="profilna-slika-title" type="text" name="title" />
              </AvGroup>
              <AvGroup>
                <Label id="createdLabel" for="profilna-slika-created">
                  <Translate contentKey="popraviApp.profilnaSlika.created">Created</Translate>
                </Label>
                <AvInput
                  id="profilna-slika-created"
                  type="datetime-local"
                  className="form-control"
                  name="created"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.profilnaSlikaEntity.created)}
                />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/profilna-slika" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  dodatniInfoUsers: storeState.dodatniInfoUser.entities,
  profilnaSlikaEntity: storeState.profilnaSlika.entity,
  loading: storeState.profilnaSlika.loading,
  updating: storeState.profilnaSlika.updating,
  updateSuccess: storeState.profilnaSlika.updateSuccess,
});

const mapDispatchToProps = {
  getDodatniInfoUsers,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ProfilnaSlikaUpdate);
