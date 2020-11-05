import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IDodatniInfoUser } from 'app/shared/model/dodatni-info-user.model';
import { getEntities as getDodatniInfoUsers } from 'app/entities/dodatni-info-user/dodatni-info-user.reducer';
import { IChat } from 'app/shared/model/chat.model';
import { getEntities as getChats } from 'app/entities/chat/chat.reducer';
import { getEntity, updateEntity, createEntity, reset } from './ucesnici.reducer';
import { IUcesnici } from 'app/shared/model/ucesnici.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IUcesniciUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const UcesniciUpdate = (props: IUcesniciUpdateProps) => {
  const [idsdodatniInfoUser, setIdsdodatniInfoUser] = useState([]);
  const [chatId, setChatId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { ucesniciEntity, dodatniInfoUsers, chats, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/ucesnici');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getDodatniInfoUsers();
    props.getChats();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.datum = convertDateTimeToServer(values.datum);

    if (errors.length === 0) {
      const entity = {
        ...ucesniciEntity,
        ...values,
        dodatniInfoUsers: mapIdList(values.dodatniInfoUsers),
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
          <h2 id="popraviApp.ucesnici.home.createOrEditLabel">
            <Translate contentKey="popraviApp.ucesnici.home.createOrEditLabel">Create or edit a Ucesnici</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : ucesniciEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="ucesnici-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="ucesnici-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="datumLabel" for="ucesnici-datum">
                  <Translate contentKey="popraviApp.ucesnici.datum">Datum</Translate>
                </Label>
                <AvInput
                  id="ucesnici-datum"
                  type="datetime-local"
                  className="form-control"
                  name="datum"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.ucesniciEntity.datum)}
                />
              </AvGroup>
              <AvGroup>
                <Label for="ucesnici-dodatniInfoUser">
                  <Translate contentKey="popraviApp.ucesnici.dodatniInfoUser">Dodatni Info User</Translate>
                </Label>
                <AvInput
                  id="ucesnici-dodatniInfoUser"
                  type="select"
                  multiple
                  className="form-control"
                  name="dodatniInfoUsers"
                  value={ucesniciEntity.dodatniInfoUsers && ucesniciEntity.dodatniInfoUsers.map(e => e.id)}
                >
                  <option value="" key="0" />
                  {dodatniInfoUsers
                    ? dodatniInfoUsers.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/ucesnici" replace color="info">
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
  chats: storeState.chat.entities,
  ucesniciEntity: storeState.ucesnici.entity,
  loading: storeState.ucesnici.loading,
  updating: storeState.ucesnici.updating,
  updateSuccess: storeState.ucesnici.updateSuccess,
});

const mapDispatchToProps = {
  getDodatniInfoUsers,
  getChats,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(UcesniciUpdate);
