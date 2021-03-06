import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, setFileData, openFile, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IDodatniInfoUser } from 'app/shared/model/dodatni-info-user.model';
import { getEntities as getDodatniInfoUsers } from 'app/entities/dodatni-info-user/dodatni-info-user.reducer';
import { getEntity, updateEntity, createEntity, setBlob, reset } from './profilna-slika.reducer';
import { IProfilnaSlika } from 'app/shared/model/profilna-slika.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IProfilnaSlikaUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ProfilnaSlikaUpdate = (props: IProfilnaSlikaUpdateProps) => {
  const [dodatniInfoUserId, setDodatniInfoUserId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { profilnaSlikaEntity, dodatniInfoUsers, loading, updating } = props;

  const { slika, slikaContentType } = profilnaSlikaEntity;

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

  const onBlobChange = (isAnImage, name) => event => {
    setFileData(event, (contentType, data) => props.setBlob(name, data, contentType), isAnImage);
  };

  const clearBlob = name => () => {
    props.setBlob(name, undefined, undefined);
  };

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.datum = convertDateTimeToServer(values.datum);

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
                <Label id="imeLabel" for="profilna-slika-ime">
                  <Translate contentKey="popraviApp.profilnaSlika.ime">Ime</Translate>
                </Label>
                <AvField id="profilna-slika-ime" type="text" name="ime" />
              </AvGroup>
              <AvGroup>
                <AvGroup>
                  <Label id="slikaLabel" for="slika">
                    <Translate contentKey="popraviApp.profilnaSlika.slika">Slika</Translate>
                  </Label>
                  <br />
                  {slika ? (
                    <div>
                      {slikaContentType ? (
                        <a onClick={openFile(slikaContentType, slika)}>
                          <img src={`data:${slikaContentType};base64,${slika}`} style={{ maxHeight: '100px' }} />
                        </a>
                      ) : null}
                      <br />
                      <Row>
                        <Col md="11">
                          <span>
                            {slikaContentType}, {byteSize(slika)}
                          </span>
                        </Col>
                        <Col md="1">
                          <Button color="danger" onClick={clearBlob('slika')}>
                            <FontAwesomeIcon icon="times-circle" />
                          </Button>
                        </Col>
                      </Row>
                    </div>
                  ) : null}
                  <input id="file_slika" type="file" onChange={onBlobChange(true, 'slika')} accept="image/*" />
                  <AvInput type="hidden" name="slika" value={slika} />
                </AvGroup>
              </AvGroup>
              <AvGroup>
                <Label id="datumLabel" for="profilna-slika-datum">
                  <Translate contentKey="popraviApp.profilnaSlika.datum">Datum</Translate>
                </Label>
                <AvInput
                  id="profilna-slika-datum"
                  type="datetime-local"
                  className="form-control"
                  name="datum"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.profilnaSlikaEntity.datum)}
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
  setBlob,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ProfilnaSlikaUpdate);
