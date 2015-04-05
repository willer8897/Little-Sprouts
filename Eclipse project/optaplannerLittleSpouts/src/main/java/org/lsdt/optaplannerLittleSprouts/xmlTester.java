package org.lsdt.optaplannerLittleSprouts;



public class xmlTester
{

	public static void main(String[] args)
	{
		xmlKing king = new xmlKing();
		king.generateStartingXML();
		king.generateSkillsXML();
		king.generateShiftXML();
		king.generateUnwantedPatternsXML();
		king.generateEmployeeContractsXML();
		king.generateEmployeeXML();
		king.generateCoverRequirementsXML();
		king.finalizeXML();
	}

}
