CREATE TABLE M_USER (ID INTEGER IDENTITY, muname VARCHAR(255) UNIQUE, mpwd VARCHAR(255), salt VARCHAR(255));
CREATE TABLE NEWSLETTER (id NUMERIC PRIMARY KEY, text VARCHAR(1000));
CREATE TABLE M_ADMIN (ID NUMERIC PRIMARY KEY, mpwd VARCHAR(255) UNIQUE);

CREATE TABLE LatexSniped (id NUMERIC IDENTITY, muser_id NUMERIC, document_id NUMERIC, content VARCHAR(1000000), content_type NUMERIC);
CREATE TABLE LatexDocuments (id NUMERIC IDENTITY, muser_id NUMERIC, documentname VARCHAR(255));
CREATE TABLE LatexType (id NUMERIC IDENTITY, type VARCHAR(255), type_opening_tag VARCHAR(255), type_closeing_tag VARCHAR(255), accessable NUMERIC);

/**
SELECT M_USER.muname,projectname,type_opening_tag,content,type_closeing_tag FROM LatexProjects JOIN LatexType, LatexDocument, M_USER
WHERE LatexProjects.id LIKE LatexDocument.document_id
AND LatexType.id LIKE LatexDocument.content_type
AND LatexProjects.muser_id LIKE M_USER.id
*/

INSERT INTO LatexDocuments (id, muser_id, documentname) VALUES (NULL, 2, 'Test Doc');
INSERT INTO LatexDocuments (id, muser_id, documentname) VALUES (NULL, 2, 'Ernsthaftes Test Dokument');
INSERT INTO LatexDocuments (id, muser_id, documentname) VALUES (NULL, 2, 'LULULULULUL');

insert into LatexSniped (id, muser_id, document_id, content, content_type) VALUES (NULL, 2, 1, 'Bla Bla Blub! Überschrift', 1);
insert into LatexSniped (id, muser_id, document_id, content, content_type) VALUES (NULL, 2, 1, 'Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum', 4);
insert into LatexSniped (id, muser_id, document_id, content, content_type) VALUES (NULL, 2, 1, 'Dolor Sit Amet Dolor Sit Amet Dolor Sit Amet Dolor Sit Amet ', 2);
insert into LatexSniped (id, muser_id, document_id, content, content_type) VALUES (NULL, 2, 1, 'Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum', 4);
insert into LatexSniped (id, muser_id, document_id, content, content_type) VALUES (NULL, 2, 1, 'Dolor Sit Amet Dolor Sit Amet Dolor Sit Amet Dolor Sit Amet ', 3);
insert into LatexSniped (id, muser_id, document_id, content, content_type) VALUES (NULL, 2, 1, 'Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem IpsumLorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem IpsumLorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum', 4);
insert into LatexSniped (id, muser_id, document_id, content, content_type) VALUES (NULL, 2, 2, 'H1 H1 H1 H1 H1', 1);
insert into LatexSniped (id, muser_id, document_id, content, content_type) VALUES (NULL, 2, 2, 'TEXT TEXT TEXT', 4);
insert into LatexSniped (id, muser_id, document_id, content, content_type) VALUES (NULL, 2, 2, 'H2 H2 H2 H2 H2', 2);
insert into LatexSniped (id, muser_id, document_id, content, content_type) VALUES (NULL, 2, 2, 'TEXT TEXT TEXT', 4);
insert into LatexSniped (id, muser_id, document_id, content, content_type) VALUES (NULL, 2, 2, 'H3 H3 H3 H3 H3', 3);

insert into LatexType (id, type, type_opening_tag, type_closeing_tag, accessable) VALUES (NULL, 'section', '\section{', '}', 1);
insert into LatexType (id, type, type_opening_tag, type_closeing_tag, accessable) VALUES (NULL, 'subsection', '\subsection{', '}', 1);
insert into LatexType (id, type, type_opening_tag, type_closeing_tag, accessable) VALUES (NULL, 'subsubsection', '\subsubsection{', '}', 1);
insert into LatexType (id, type, type_opening_tag, type_closeing_tag, accessable) VALUES (NULL, 'text', '<text>', '}', 1);
insert into LatexType (id, type, type_opening_tag, type_closeing_tag, accessable) VALUES (NULL, 'Titel', '\titel{', '}', 0);
insert into LatexType (id, type, type_opening_tag, type_closeing_tag, accessable) VALUES (NULL, 'Author', '\author{', '}', 0);
insert into LatexType (id, type, type_opening_tag, type_closeing_tag, accessable) VALUES (NULL, 'Beginn', '\begin{', '}', 0);
insert into LatexType (id, type, type_opening_tag, type_closeing_tag, accessable) VALUES (NULL, 'Ende', '\end{', '}', 0);


insert into M_USER (ID,muname,mpwd) values (1,'never','login');

insert into NEWSLETTER (id,text) values (1,'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet. Quisque rutrum. Aenean imperdiet. Etiam ultricies nisi vel augue. Curabitur ullamcorper ultricies nisi. Nam eget dui. Etiam rhoncus.');
insert into NEWSLETTER (id,text) values (2,'Auch gibt es niemanden, der den Schmerz an sich liebt, sucht oder wünscht, nur, weil er Schmerz ist, es sei denn, es kommt zu zufälligen Umständen, in denen Mühen und Schmerz ihm große Freude bereiten können. Um ein triviales Beispiel zu nehmen, wer von uns unterzieht sich je anstrengender körperlicher Betätigung, außer um Vorteile daraus zu ziehen? Aber wer hat irgend ein Recht, einen Menschen zu tadeln, der die Entscheidung trifft, eine Freude zu genießen, die keine unangenehmen Folgen hat, oder einen, der Schmerz vermeidet, welcher keine daraus resultierende Freude nach sich zieht? Auch gibt es niemanden, der den Schmerz an sich liebt, sucht oder wünscht, nur, weil er Schmerz ist, es sei denn, es kommt zu zufälligen Umständen, in denen Mühen und Schmerz ihm große Freude bereiten können. Um ein triviales Beispiel zu nehmen, wer von uns unterzieht sich je anstrengender körperlicher Betätigung, außer um Vorteile daraus zu ziehen?');
insert into NEWSLETTER (id,text) values (3,'Jemand musste Josef K. verleumdet haben, denn ohne dass er etwas Böses getan hätte, wurde er eines Morgens verhaftet. Wie ein Hund!  sagte er, es war, als sollte die Scham ihn überleben. Als Gregor Samsa eines Morgens aus unruhigen Träumen erwachte, fand er sich in seinem Bett zu einem ungeheueren Ungeziefer verwandelt. Und es war ihnen wie eine Bestätigung ihrer neuen Träume und guten Absichten, als am Ziele ihrer Fahrt die Tochter als erste sich erhob und ihren jungen Körper dehnte. Es ist ein eigentümlicher Apparat, sagte der Offizier zu dem Forschungsreisenden und überblickte mit einem gewissermaßen bewundernden Blick den ihm doch wohlbekannten Apparat. Sie hätten noch ins Boot springen können, aber der Reisende hob ein schweres, geknotetes Tau vom Boden, drohte ihnen damit und hielt sie dadurch von dem Sprunge ab. In den letzten Jahrzehnten ist das Interesse an Hungerkünstlern sehr zurückgegangen.');
insert into NEWSLETTER (id,text) values (4,'Vierter Newsletter');
insert into NEWSLETTER (id,text) values (5,'Es gibt im Moment in diese Mannschaft, oh, einige Spieler vergessen ihnen Profi was sie sind. Ich lese nicht sehr viele Zeitungen, aber ich habe gehört viele Situationen. Erstens: wir haben nicht offensiv gespielt. Es gibt keine deutsche Mannschaft spielt offensiv und die Name offensiv wie Bayern. Letzte Spiel hatten wir in Platz drei Spitzen: Elber, Jancka und dann Zickler. Wir müssen nicht vergessen Zickler. Zickler ist eine Spitzen mehr, Mehmet eh mehr Basler. Ist klar diese Wörter, ist möglich verstehen, was ich hab gesagt? Danke. Offensiv, offensiv ist wie machen wir in Platz. Zweitens: ich habe erklärt mit diese zwei Spieler: nach Dortmund brauchen vielleicht Halbzeit Pause. Ich habe auch andere Mannschaften gesehen in Europa nach diese Mittwoch. Ich habe gesehen auch zwei Tage die Training. Ein Trainer ist nicht ein Idiot! Ein Trainer sei sehen was passieren in Platz.');
insert into NEWSLETTER (id,text) values (6,'Er hörte leise Schritte hinter sich. Das bedeutete nichts Gutes. Wer würde ihm schon folgen, spät in der Nacht und dazu noch in dieser engen Gasse mitten im übel beleumundeten Hafenviertel? Gerade jetzt, wo er das Ding seines Lebens gedreht hatte und mit der Beute verschwinden wollte! Hatte einer seiner zahllosen Kollegen dieselbe Idee gehabt, ihn beobachtet und abgewartet, um ihn nun um die Früchte seiner Arbeit zu erleichtern? Oder gehörten die Schritte hinter ihm zu einem der unzähligen Gesetzeshüter dieser Stadt, und die stählerne Acht um seine Handgelenke würde gleich zuschnappen? Er konnte die Aufforderung stehen zu bleiben schon hören. Gehetzt sah er sich um. Plötzlich erblickte er den schmalen Durchgang. Blitzartig drehte er sich nach rechts und verschwand zwischen den beiden Gebäuden. Beinahe wäre er dabei über den umgestürzten Mülleimer gefallen, der mitten im Weg lag.');
insert into NEWSLETTER (id,text) values (7,'Dies ist ein Typoblindtext. An ihm kann man sehen, ob alle Buchstaben da sind und wie sie aussehen. Manchmal benutzt man Worte wie Hamburgefonts, Rafgenduks oder Handgloves, um Schriften zu testen. Manchmal Sätze, die alle Buchstaben des Alphabets enthalten - man nennt diese Sätze Pangrams. Sehr bekannt ist dieser: The quick brown fox jumps over the lazy old dog. Oft werden in Typoblindtexte auch fremdsprachige Satzteile eingebaut (AVAIL® and Wefox™ are testing aussi la Kerning), um die Wirkung in anderen Sprachen zu testen. In Lateinisch sieht zum Beispiel fast jede Schrift gut aus. Quod erat demonstrandum. Seit 1975 fehlen in den meisten Testtexten die Zahlen, weswegen nach TypoGb. 204 § ab dem Jahr 2034 Zahlen in 86 der Texte zur Pflicht werden. Nichteinhaltung wird mit bis zu 245 € oder 368 $ bestraft. Genauso wichtig in sind mittlerweile auch Âçcèñtë, die in neueren Schriften aber fast immer enthalten sind.');


insert into M_ADMIN (ID,mpwd) values (1,'723fd79229e4cf7a5842583290e6712a3ad8d2fe5da1e8e9087ec06cb2901239');