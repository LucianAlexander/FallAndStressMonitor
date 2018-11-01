# FallAndStressMonitor

Tesi di Laurea Lemnariu Lucian Alexandru

La soluzione proposta in questo lavoro di tesi prevede di superare i limiti delle attuali app e
dei sistemi domotici, attraverso un sistema indossabile non invasivo e low cost per l’individuo a
rischio cadute, capace di rilevare le stesse e richiedere il relativo intervento, notificato attraverso
una opportuna app mobile in uso al caregiver. Oltre a questo, il sistema prevede la misurazione e
l’analisi di parametri biomedici, basati sulla misura della risposta galvanica, al fine di determinare
lo stato emotivo dell’individuo e di identificare eventuali fasi acute di stress. In particolare negli
anziani stress e ansia possono presentarsi per riverse ragioni, quali un problema fisico, la sensazione
di isolamento, la perdita dell’attività lavorativa, il diminuito guadagno, la perdita del prestigio e del
ruolo sociale derivante dal lavoro svolto. Inoltre, secondo una ricerca del Veterans Affairs
Medical Center di Minneapolis è stata pubblicata sulla rivista Age and Ageing, Lo stress aumenta
in modo significativo il rischio di cadute negli uomini anziani. Infine il sistema misura la
frequenza cardiaca dell’individuo in tempo continuo. Anche in questo caso, in caso di livelli di
frequenza considerati anomali dal sistema, viene inviato uno specifico segnale di alert al caregiver.
Da un punto di visto architettonico la soluzione progettuale presentata è basata su un sistema
hardware su base Arduino e Libelium MySignals e una applicazione software Android. I principali
moduli che compongono il sistema sono:
1. Dispositivo per l’assistito,composto da Arduino uno e shield MySygnals,provvisto di tre sensori integrati per 
l’acquisizione dei parametri biomedici, i cui dati vengono inviati verso un server di acquisizione dati.
2. Server centrale, incaricato di ricevere i dati dal dispositivo mobile, salvarli in una base dati 7 ,
analizzarli, e inviare notifiche all’applicazione mobile, attraverso un servizio di
messaggistica (basato su Firebase Cloud Messaging).
3. Applicazione mobile Android in uso al caregiver, responsabile di acquisire i dati dal server
centrale e di ricevere notifiche in caso di possibili cadute del proprio assistito, frequenza
cardiaca irregolare o di eventi acuti di stress.
