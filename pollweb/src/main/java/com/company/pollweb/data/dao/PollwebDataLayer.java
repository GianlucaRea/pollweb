 package com.company.pollweb.data.dao;

import com.company.pollweb.data.models.*;

import com.company.pollweb.framework.data.DataException;
import com.company.pollweb.framework.data.DataLayer;
import javax.sql.DataSource;
import java.sql.SQLException;

    public class PoolWebDataLayer extends DataLayer {

        public PoolWebDataLayer(DataSource dataSource) throws SQLException {
            super(dataSource);
        }

        @Override
        public void init() throws DataException {
            registerDAO(Utente.class, new UserDAO_MySQL(this));
            registerDAO(Sondaggio.class, new PollDAO_MySQL(this));
            registerDAO(Domanda.class, new QuestionDAO_MySQL(this));
            registerDAO(Risposta.class, new AnswerDAO_MySQL(this));
            registerDAO(Ruolo.class, new RoleDAO_MySQL(this));
            registerDAO(Compilazione.class, new InstanceDAO_MySQL(this));
        }

        public UtenteDao getUserDAO() { return ((UtenteDao) getDAO(Utente.class)); }
        public SondaggioDao getPollDAO() { return ((SondaggioDao) getDAO(Sondaggio.class)); }
        public DomandaDao getQuestionDAO() { return ((DomandaDao) getDAO(Domanda.class)); }
        public RispostaDao getAnswerDAO() { return  ((RispostaDao) getDAO(Risposta.class)); }
        public RuoloDao getRoleDAO() { return ((RuoloDao) getDAO(Ruolo.class)); }
        public CompilazioneDao getInstanceDAO() { return ((CompilazioneDao) getDAO(Compilazione.class)); }
    }
}
