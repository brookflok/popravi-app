import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, setFileData, openFile, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IArtikl } from 'app/shared/model/artikl.model';
import { getEntities as getArtikls } from 'app/entities/artikl/artikl.reducer';
import { getEntity, updateEntity, createEntity, setBlob, reset } from './main-slika.reducer';
import { IMainSlika } from 'app/shared/model/main-slika.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IMainSlikaUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const MainSlikaUpdate = (props: IMainSlikaUpdateProps) => {
  const [artiklId, setArtiklId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { mainSlikaEntity, artikls, loading, updating } = props;

  const { slika, slikaContentType } = mainSlikaEntity;

  const handleClose = () => {
    props.history.push('/main-slika');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getArtikls();
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
        ...mainSlikaEntity,
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
          <h2 id="popraviApp.mainSlika.home.createOrEditLabel">
            <Translate contentKey="popraviApp.mainSlika.home.createOrEditLabel">Create or edit a MainSlika</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : mainSlikaEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="main-slika-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="main-slika-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="imeLabel" for="main-slika-ime">
                  <Translate contentKey="popraviApp.mainSlika.ime">Ime</Translate>
                </Label>
                <AvField id="main-slika-ime" type="text" name="ime" />
              </AvGroup>
              <AvGroup>
                <AvGroup>
                  <Label id="slikaLabel" for="slika">
                    <Translate contentKey="popraviApp.mainSlika.slika">Slika</Translate>
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
                <Label id="datumLabel" for="main-slika-datum">
                  <Translate contentKey="popraviApp.mainSlika.datum">Datum</Translate>
                </Label>
                <AvInput
                  id="main-slika-datum"
                  type="datetime-local"
                  className="form-control"
                  name="datum"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.mainSlikaEntity.datum)}
                />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/main-slika" replace color="info">
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
  artikls: storeState.artikl.entities,
  mainSlikaEntity: storeState.mainSlika.entity,
  loading: storeState.mainSlika.loading,
  updating: storeState.mainSlika.updating,
  updateSuccess: storeState.mainSlika.updateSuccess,
});

const mapDispatchToProps = {
  getArtikls,
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MainSlikaUpdate);
