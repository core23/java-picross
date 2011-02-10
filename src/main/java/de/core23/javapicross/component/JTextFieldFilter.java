package de.core23.javapicross.component;

import javax.swing.text.*;

public class JTextFieldFilter extends PlainDocument {
	private static final long serialVersionUID = 1L;

	public static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz"; //$NON-NLS-1$

	public static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; //$NON-NLS-1$

	public static final String ALPHA = LOWERCASE + UPPERCASE;

	public static final String NUMERIC = "0123456789"; //$NON-NLS-1$

	public static final String FLOAT = NUMERIC + "."; //$NON-NLS-1$

	public static final String ALPHA_NUMERIC = ALPHA + NUMERIC;

	protected String acceptedChars = null;

	protected boolean negativeAccepted = false;

	public JTextFieldFilter() {
		this(ALPHA_NUMERIC);
	}

	public JTextFieldFilter(String acceptedchars) {
		acceptedChars = acceptedchars;
	}

	public void setNegativeAccepted(boolean negativeaccepted) {
		if (acceptedChars.equals(NUMERIC) || acceptedChars.equals(FLOAT) || acceptedChars.equals(ALPHA_NUMERIC)) {
			negativeAccepted = negativeaccepted;
			acceptedChars += "-"; //$NON-NLS-1$
		}
	}

	public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
		if (str == null)
			return;

		if (acceptedChars.equals(UPPERCASE))
			str = str.toUpperCase();
		else if (acceptedChars.equals(LOWERCASE))
			str = str.toLowerCase();

		for (int i = 0; i < str.length(); i++) {
			if (acceptedChars.indexOf(String.valueOf(str.charAt(i))) == -1)
				return;
		}

		if (acceptedChars.equals(FLOAT) || (acceptedChars.equals(FLOAT + "-") && negativeAccepted)) { //$NON-NLS-1$
			if (str.indexOf(".") != -1) { //$NON-NLS-1$
				if (getText(0, getLength()).indexOf(".") != -1) //$NON-NLS-1$
					return;
			}
		}

		if (negativeAccepted && str.indexOf("-") != -1) { //$NON-NLS-1$
			if (str.indexOf("-") != 0 || offset != 0) //$NON-NLS-1$
				return;
		}

		super.insertString(offset, str, attr);
	}
}