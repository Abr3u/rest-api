package liquido.aws.rest_api;

public class SendDocMetacaseTest{
	private final static String data = "<Request><Company>CANDOR</Company><System>CANDOR</System><Bank>someBank</Bank><MessageId>00P25000002hDHsEAM</MessageId><Proposal>SEPA-PT-002891_2018-02-07_SalesCreditNotes.xml</Proposal><Type>SPCT</Type><User>Célia Pedro</User><CreationDate>2018-04-29 18:21:43</CreationDate><Message><![CDATA[<?xml version=\"1.0\" encoding=\"UTF-8\"?><Document xmlns=\"urn:iso:std:iso:20022:tech:xsd:pain.001.001.03\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><CstmrCdtTrfInitn><GrpHdr><MsgId>SEPA-PT-002891</MsgId><CreDtTm>2018-04-29T19:21:41</CreDtTm><NbOfTxs>2</NbOfTxs><CtrlSum>181.32</CtrlSum><InitgPty><Nm>CANDOR RENTING DE EQUIPAMENTOS SA</Nm><Id><PrvtId><Othr><Id>PT09ZZZ111901</Id></Othr></PrvtId></Id></InitgPty></GrpHdr><PmtInf><PmtInfId>SEPA-PT-002891</PmtInfId><PmtMtd>TRF</PmtMtd><NbOfTxs>2</NbOfTxs><CtrlSum>181.32</CtrlSum><PmtTpInf><CtgyPurp><Cd>OTHR</Cd></CtgyPurp></PmtTpInf><ReqdExctnDt>2018-02-07</ReqdExctnDt><Dbtr><Nm>CANDOR RENTING DE EQUIPAMENTOS SA</Nm><PstlAdr><Ctry>PT</Ctry><AdrLine>Avenida Dom Joao II, 50, 1990-095 Lisboa, Portugal</AdrLine></PstlAdr></Dbtr><DbtrAcct><Id><IBAN>PT50001000005300067000263</IBAN></Id></DbtrAcct><DbtrAgt><FinInstnId><BIC>BBPIPTPL</BIC></FinInstnId></DbtrAgt><CdtTrfTxInf><PmtId><EndToEndId>SCR001534</EndToEndId></PmtId><Amt><InstdAmt Ccy=\"EUR\">3.00</InstdAmt></Amt><CdtrAgt><FinInstnId><BIC>BBPIPTPL</BIC></FinInstnId></CdtrAgt><Cdtr><Nm>SERRALHARIA MEC?NICA - J?LIO CASTRO, LDA</Nm></Cdtr><CdtrAcct><Id><IBAN>PT50001000002207103000178</IBAN></Id></CdtrAcct><RmtInf><Ustrd>Payment for Credit Note: 501994467</Ustrd></RmtInf></CdtTrfTxInf><CdtTrfTxInf><PmtId><EndToEndId>SCR001546</EndToEndId></PmtId><Amt><InstdAmt Ccy=\"EUR\">178.32</InstdAmt></Amt><CdtrAgt><FinInstnId><BIC>BBVAPTPL</BIC></FinInstnId></CdtrAgt><Cdtr><Nm>SALESLAND PORTUGAL, UNIPESSOAL, LDA</Nm></Cdtr><CdtrAcct><Id><IBAN>PT50001900410020004524586</IBAN></Id></CdtrAcct><RmtInf><Ustrd>Payment for Credit Note: 507170830</Ustrd></RmtInf></CdtTrfTxInf></PmtInf></CstmrCdtTrfInitn></Document>]]></Message><Signature>kRGCPmcoKpNjjqoaqP8+HHm89qcczbN0zQvJChkBWi4pvMAedc7UCPBc9nZD5OaBhSz94j7x7kv8JFmoPVL+yOh+ak5wprFV5inGLR9gO0diSLo8riNcBMfVSS8FA6gR3UPmSd30kGFc2mM1ZYQrWU7qO6Uzo0bR3tuk9kTeck46XIaPGYeqrM6cG0KDvvFc5JSTBUlsh5IT87elMCqQ/uTBLKLDax33dovQbt6Cg0jewgYlOxktxogKS1ZxOL5fVs2ZyREpNcsLMa2zXlEk9zlaN7M5OnPqCkoAgZlBE8w57RqJnKsnX2GuxvDyuOUt7tI//hdaIRHaBd+Fqj6uJw==</Signature></Request>";
	private final static String badData = "<Request><Company>CANDOR</Company><System>CANDOR</System><Bank>someBank</Bank><MessageId>00P25000002hDHsEAM</MessageId><Proposal>SEPA-PT-002891_2018-02-07_SalesCreditNotes.xml</Proposal><Type>SPCT</Type><User>Célia Pedro</User><CreationDate>2018-04-29 18:21:43</CreationDate><Message><![CDATA[<?xml version=\"1.0\" encoding=\"UTF-8\"?><Document xmlns=\"urn:iso:std:iso:20022:tech:xsd:pain.001.001.03\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><CstmrCdtTrfInitn><GrpHdr><MsgId>SEPA-PT-002891</MsgId><CreDtTm>2018-04-29T19:21:41</CreDtTm><NbOfTxs>2</NbOfTxs><CtrlSum>181.32</CtrlSum><InitgPty><Nm>CANDOR RENTING DE EQUIPAMENTOS SA</Nm><Id><PrvtId><Othr><Id>PT09ZZZ111901</Id></Othr></PrvtId></Id></InitgPty></GrpHdr><PmtInf><PmtInfId>SEPA-PT-002891</PmtInfId><PmtMtd>TRF</PmtMtd><NbOfTxs>2</NbOfTxs><CtrlSum>181.32</CtrlSum><PmtTpInf><CtgyPurp><Cd>OTHR</Cd></CtgyPurp></PmtTpInf><ReqdExctnDt>2018-02-07</ReqdExctnDt><Dbtr><Nm>CANDOR RENTING DE EQUIPAMENTOS SA</Nm><PstlAdr><Ctry>PT</Ctry><AdrLine>Avenida Dom Joao II, 50, 1990-095 Lisboa, Portugal</AdrLine></PstlAdr></Dbtr><DbtrAcct><Id><IBAN>PT50001000005300067000263</IBAN></Id></DbtrAcct><DbtrAgt><FinInstnId><BIC>BBPIPTPL</BIC></FinInstnId></DbtrAgt><CdtTrfTxInf><PmtId><EndToEndId>SCR001534</EndToEndId></PmtId><Amt><InstdAmt Ccy=\"EUR\">3.00</InstdAmt></Amt><CdtrAgt><FinInstnId><BIC>BBPIPTPL</BIC></FinInstnId></CdtrAgt><Cdtr><Nm>SERRALHARIA MEC?NICA - J?LIO CASTRO, LDA</Nm></Cdtr><CdtrAcct><Id><IBAN>PT50001000002207103000178</IBAN></Id></CdtrAcct><RmtInf><Ustrd>Payment for Credit Note: 501994467</Ustrd></RmtInf></CdtTrfTxInf><CdtTrfTxInf><PmtId><EndToEndId>SCR001546</EndToEndId></PmtId><Amt><InstdAmt Ccy=\"EUR\">178.32</InstdAmt></Amt><CdtrAgt><FinInstnId><BIC>BBVAPTPL</BIC></FinInstnId></CdtrAgt><Cdtr><Nm>SALESLAND PORTUGAL, UNIPESSOAL, LDA</Nm></Cdtr><CdtrAcct><Id><IBAN>PT50001900410020004524586</IBAN></Id></CdtrAcct><RmtInf><Ustrd>Payment for Credit Note: 507170830</Ustrd></RmtInf></CdtTrfTxInf></PmtInf></CstmrCdtTrfInitn></Document>]]></Message><Signature>kRGCPmcoKpNjjqoaqP8+HHm89qcczbN0zQvJChkBWi4pvMAedc7UCPBc9nZD5OaBhSz94j7x7kv8JFmoPVL+yOh+ak5wprFV5inGLR9gO0diSLo8riNcBMfVSS8FA6gR3UPmSd30kGFc2mM1ZYQrWU7qO6Uzo0bR3tuk9kTeck46XIaPGYeqrM6cG0KDvvFc5JSTBUlsh5IT87elMCqQ/uTBLKLDax33dovQbt6Cg0jewgYlOxktxogKS1ZxOL5fVs2ZyREpNcsLMa2zXlEk9zlaN7M5OnPqCkoAgZlBE8w57RqJnKsnX2GuxvDyuOUt7tI//hdaIRHaBd+Fqj6uJa==</Signature></Request>";
	
	private final static int goodCode = 200;
	private final static int badCode = 500;
	/*
	@Test
	public void shouldSendDocument() {
		XmlRequest req = new XmlRequest();
		req.body = data;
		
		assertTrue(req.isValid());
		
		SendDocMetacaseHandler handler = new SendDocMetacaseHandler();
		Answer answer = handler.process(req, Collections.emptyMap());
		
		assertEquals(answer.getCode(), goodCode);
		assert(answer.getBody().contains("messageID") && answer.getBody().contains("result") 
				&& answer.getBody().contains("message") && answer.getBody().contains("system"));
	}
	
	@Test
	public void shouldFailInvalidSignature() {
		XmlRequest req = new XmlRequest();
		req.body = badData;
		
		assertTrue(req.isValid());
		
		SendDocMetacaseHandler handler = new SendDocMetacaseHandler();
		Answer answer = handler.process(req, Collections.emptyMap());
		
		assertEquals(answer.getCode(), badCode);
		assertEquals(answer.getBody(), "Invalid Request - Bad Signature");
	}*/
}
