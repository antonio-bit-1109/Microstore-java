- [x] criptazione della password in fase di registrazione
- [x] configurazione di un middleware per gestire la validazione dei DTO in entrata in un controller
- [x] configurazione di spring security per poter inserire bcrypt per la criptazione della password
- [x] scrittura del bean nel file di configurazione per poter aver disponibile bcrypt nello userservice
- [x] decriptazione della password in fase di login per confrontarla con quella in arrivo dal client
- [x] capire cosa sia ModelMapper
- [] implementa i model mapper per mappare facilmente tra entity e DTO
- [] studia lobonk come funziona , evitare utilizzo di codice java ma utilizzando annotation fai tutto
- [x] spostare la logica dell autenticazione login bla bla servizi dedicati nel package dell auth
- [x] get dell id per fare delete del prodotto
- [x] completa i processamenti ordine shipped , processing completed ... bla bla
- [x] utilizzare i DTO per far uscire gli oggetti entità dai servizi (da modificare nello usercontroller, userservice)


- [x] completa l edit ordine e tutte le crud sull ordine
- [x] implementa jwtToken con tutti i crismi
- [] implementa il cart item ??
- [] controlla se i dati passati al token e poi all oggetto Authentication posso riprenderli dal controller
- [] modifica il codice affinchè io riesca a passare el token anche il ruolo e
  riesca ad utilizarlo per poter accedere agli endpoint pasandomi sul ruolo

-[x] controlla che stock sia sufficiente e rimuovi quantita richiesta dal client
-[x] controllo che userid arrivato dal client sia lo stesso dell owner dell ordine su db
- [] fai le get dei vari product, order bla bla e getall (alcune get all tipo getall users possono farlo solo gli
  admin -- ovviamente autenticati )
- [] crea un controller per quegli endpoint richiamabili sono dagli admin
- [] fai le varie getall di un po tutte le entity
- [] crea un adminService dal quale richiamare i repository
- [] completa i metodi del mapper per generare DTO usando reflection

-[] devi castare qualcosa a Users altrimenti ricevi l'allocazione di memoria invece dei dati effettivi dello users ???
-[] controlla e risolvi problema del carrello infinito-gigamega - struttura per bene i DTOs in modo da mappare per bene
le risposte ottenute.

-[] fai get e get-all di ordini e carrelli.
-[] controllare tramite Token se l'utente che fa le get è autorizzato a vedere quel carrello
(se utente possessore del carrello possiede lo stesso id salvato nel token)