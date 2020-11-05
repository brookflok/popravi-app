import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ILokacija } from 'app/shared/model/lokacija.model';
import { getEntities as getLokacijas } from 'app/entities/lokacija/lokacija.reducer';
import { IPotreba } from 'app/shared/model/potreba.model';
import { getEntities as getPotrebas } from 'app/entities/potreba/potreba.reducer';
import { IUsluga } from 'app/shared/model/usluga.model';
import { getEntities as getUslugas } from 'app/entities/usluga/usluga.reducer';
import { IGalerija } from 'app/shared/model/galerija.model';
import { getEntities as getGalerijas } from 'app/entities/galerija/galerija.reducer';
import { IMainSlika } from 'app/shared/model/main-slika.model';
import { getEntities as getMainSlikas } from 'app/entities/main-slika/main-slika.reducer';
import { IInformacije } from 'app/shared/model/informacije.model';
import { getEntities as getInformacijes } from 'app/entities/informacije/informacije.reducer';
import { IGrupacijaPitanja } from 'app/shared/model/grupacija-pitanja.model';
import { getEntities as getGrupacijaPitanjas } from 'app/entities/grupacija-pitanja/grupacija-pitanja.reducer';
import { IDodatniInfoUser } from 'app/shared/model/dodatni-info-user.model';
import { getEntities as getDodatniInfoUsers } from 'app/entities/dodatni-info-user/dodatni-info-user.reducer';
import { getEntity, updateEntity, createEntity, reset } from './artikl.reducer';
import { IArtikl } from 'app/shared/model/artikl.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IArtiklUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ArtiklUpdate = (props: IArtiklUpdateProps) => {
  const [lokacijaId, setLokacijaId] = useState('0');
  const [potrebaId, setPotrebaId] = useState('0');
  const [uslugaId, setUslugaId] = useState('0');
  const [galerijaId, setGalerijaId] = useState('0');
  const [mainSlikaId, setMainSlikaId] = useState('0');
  const [informacijeId, setInformacijeId] = useState('0');
  const [grupacijaPitanjaId, setGrupacijaPitanjaId] = useState('0');
  const [dodatniinfouserId, setDodatniinfouserId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const {
    artiklEntity,
    lokacijas,
    potrebas,
    uslugas,
    galerijas,
    mainSlikas,
    informacijes,
    grupacijaPitanjas,
    dodatniInfoUsers,
    loading,
    updating,
  } = props;

  const handleClose = () => {
    props.history.push('/artikl');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getLokacijas();
    props.getPotrebas();
    props.getUslugas();
    props.getGalerijas();
    props.getMainSlikas();
    props.getInformacijes();
    props.getGrupacijaPitanjas();
    props.getDodatniInfoUsers();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...artiklEntity,
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
          <h2 id="popraviApp.artikl.home.createOrEditLabel">
            <Translate contentKey="popraviApp.artikl.home.createOrEditLabel">Create or edit a Artikl</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : artiklEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="artikl-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="artikl-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="imeLabel" for="artikl-ime">
                  <Translate contentKey="popraviApp.artikl.ime">Ime</Translate>
                </Label>
                <AvField id="artikl-ime" type="text" name="ime" />
              </AvGroup>
              <AvGroup>
                <Label id="kratkiOpisLabel" for="artikl-kratkiOpis">
                  <Translate contentKey="popraviApp.artikl.kratkiOpis">Kratki Opis</Translate>
                </Label>
                <AvField id="artikl-kratkiOpis" type="text" name="kratkiOpis" />
              </AvGroup>
              <AvGroup>
                <Label id="detaljniOpisLabel" for="artikl-detaljniOpis">
                  <Translate contentKey="popraviApp.artikl.detaljniOpis">Detaljni Opis</Translate>
                </Label>
                <AvField id="artikl-detaljniOpis" type="text" name="detaljniOpis" />
              </AvGroup>
              <AvGroup check>
                <Label id="majstorLabel">
                  <AvInput id="artikl-majstor" type="checkbox" className="form-check-input" name="majstor" />
                  <Translate contentKey="popraviApp.artikl.majstor">Majstor</Translate>
                </Label>
              </AvGroup>
              <AvGroup check>
                <Label id="postojiLabel">
                  <AvInput id="artikl-postoji" type="checkbox" className="form-check-input" name="postoji" />
                  <Translate contentKey="popraviApp.artikl.postoji">Postoji</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label for="artikl-lokacija">
                  <Translate contentKey="popraviApp.artikl.lokacija">Lokacija</Translate>
                </Label>
                <AvInput id="artikl-lokacija" type="select" className="form-control" name="lokacija.id">
                  <option value="" key="0" />
                  {lokacijas
                    ? lokacijas.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="artikl-potreba">
                  <Translate contentKey="popraviApp.artikl.potreba">Potreba</Translate>
                </Label>
                <AvInput id="artikl-potreba" type="select" className="form-control" name="potreba.id">
                  <option value="" key="0" />
                  {potrebas
                    ? potrebas.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="artikl-usluga">
                  <Translate contentKey="popraviApp.artikl.usluga">Usluga</Translate>
                </Label>
                <AvInput id="artikl-usluga" type="select" className="form-control" name="usluga.id">
                  <option value="" key="0" />
                  {uslugas
                    ? uslugas.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="artikl-galerija">
                  <Translate contentKey="popraviApp.artikl.galerija">Galerija</Translate>
                </Label>
                <AvInput id="artikl-galerija" type="select" className="form-control" name="galerija.id">
                  <option value="" key="0" />
                  {galerijas
                    ? galerijas.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="artikl-mainSlika">
                  <Translate contentKey="popraviApp.artikl.mainSlika">Main Slika</Translate>
                </Label>
                <AvInput id="artikl-mainSlika" type="select" className="form-control" name="mainSlika.id">
                  <option value="" key="0" />
                  {mainSlikas
                    ? mainSlikas.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="artikl-informacije">
                  <Translate contentKey="popraviApp.artikl.informacije">Informacije</Translate>
                </Label>
                <AvInput id="artikl-informacije" type="select" className="form-control" name="informacije.id">
                  <option value="" key="0" />
                  {informacijes
                    ? informacijes.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="artikl-grupacijaPitanja">
                  <Translate contentKey="popraviApp.artikl.grupacijaPitanja">Grupacija Pitanja</Translate>
                </Label>
                <AvInput id="artikl-grupacijaPitanja" type="select" className="form-control" name="grupacijaPitanja.id">
                  <option value="" key="0" />
                  {grupacijaPitanjas
                    ? grupacijaPitanjas.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="artikl-dodatniinfouser">
                  <Translate contentKey="popraviApp.artikl.dodatniinfouser">Dodatniinfouser</Translate>
                </Label>
                <AvInput id="artikl-dodatniinfouser" type="select" className="form-control" name="dodatniinfouser.id">
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
              <Button tag={Link} id="cancel-save" to="/artikl" replace color="info">
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
  lokacijas: storeState.lokacija.entities,
  potrebas: storeState.potreba.entities,
  uslugas: storeState.usluga.entities,
  galerijas: storeState.galerija.entities,
  mainSlikas: storeState.mainSlika.entities,
  informacijes: storeState.informacije.entities,
  grupacijaPitanjas: storeState.grupacijaPitanja.entities,
  dodatniInfoUsers: storeState.dodatniInfoUser.entities,
  artiklEntity: storeState.artikl.entity,
  loading: storeState.artikl.loading,
  updating: storeState.artikl.updating,
  updateSuccess: storeState.artikl.updateSuccess,
});

const mapDispatchToProps = {
  getLokacijas,
  getPotrebas,
  getUslugas,
  getGalerijas,
  getMainSlikas,
  getInformacijes,
  getGrupacijaPitanjas,
  getDodatniInfoUsers,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ArtiklUpdate);
