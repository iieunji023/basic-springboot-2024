/* 생성된 시퀀스 확인 쿼리 */
SELECT * FROM USER_SEQUENCES WHERE SEQUENCE_NAME = 'SEQ_TODOS';

INSERT INTO PKNUSB.TODOS
(
	TNO 
	,TITLE 
	,DUEDATE 
	,WRITER 
	,ISDONE 
)
VALUES
(
	SEQ_TODOS.NEXTVAL
	,'피자먹기'
	,TO_DATE('2024-06-13 21:00:00', 'YYYY-MM-DD HH24:MI:SS')
	,'홍길동'
	,0
);

SELECT * FROM TODOS WHERE TNO=1;

UPDATE TODOS SET 
	TITLE = '피자 두조각 먹기'
	,ISDONE = 1 
WHERE TNO = 1;

DELETE FROM TODOS WHERE TNO = 1;