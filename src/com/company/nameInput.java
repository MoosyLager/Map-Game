package com.company;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.util.Locale;

/**
 * This is just the accompanying java file to the nameInput dialog box
 * It contains the listener for when the OK button is pressed
 * It also contains a method which allows the name of entered into the dialog
 * box
 * to be stored in a variable in GUI.java
 */
public class nameInput extends JDialog {
        private JPanel contentPane;
        private JButton buttonOK;
        private JTextField nameInputField;
        private String name;

        public nameInput() {
                setContentPane(contentPane);
                setModal(true);
                getRootPane().setDefaultButton(buttonOK);

                buttonOK.addActionListener(e -> onOK());
        }

        private void onOK() {
                // checking if name is not blank and not longer than 20 characters
                if (!nameInputField.getText().isEmpty() && nameInputField.getText().length() <= 20) {
                        name = nameInputField.getText();
                        dispose();
                }
        }

        // allows the name entered into the dialog box to be stored in a variable in
        // GUI.java
        public String showDialog() {
                return name;
        }

        {
                // GUI initializer generated by IntelliJ IDEA GUI Designer
                // >>> IMPORTANT!! <<<
                // DO NOT EDIT OR ADD ANY CODE HERE!
                $$$setupUI$$$();
        }

        /**
         * Method generated by IntelliJ IDEA GUI Designer
         * >>> IMPORTANT!! <<<
         * DO NOT edit this method OR call it in your code!
         *
         * @noinspection ALL
         */
        private void $$$setupUI$$$() {
                contentPane = new JPanel();
                contentPane.setLayout(
                                new com.intellij.uiDesigner.core.GridLayoutManager(2, 1, new Insets(10, 10, 10, 10), -1,
                                                -1));
                final JPanel panel1 = new JPanel();
                panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1,
                                -1));
                contentPane.add(panel1,
                                new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1,
                                                com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
                                                com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH,
                                                com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
                                                                | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW,
                                                1, null, null, null, 0, false));
                final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
                panel1.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1,
                                com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
                                com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL,
                                com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null,
                                0, false));
                final JPanel panel2 = new JPanel();
                panel2.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1,
                                -1));
                panel1.add(panel2,
                                new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1,
                                                com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
                                                com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH,
                                                com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
                                                                | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW,
                                                com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
                                                                | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW,
                                                null, null, null, 0, false));
                buttonOK = new JButton();
                buttonOK.setText("OK");
                panel2.add(buttonOK,
                                new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1,
                                                com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
                                                com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL,
                                                com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
                                                                | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW,
                                                com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null,
                                                null, null, 0, false));
                final JPanel panel3 = new JPanel();
                panel3.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1,
                                -1));
                contentPane.add(panel3,
                                new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1,
                                                com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
                                                com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH,
                                                com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
                                                                | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW,
                                                com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
                                                                | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW,
                                                null, null, null, 0, false));
                nameInputField = new JTextField();
                Font nameInputFieldFont = this.$$$getFont$$$(null, -1, 18, nameInputField.getFont());
                if (nameInputFieldFont != null)
                        nameInputField.setFont(nameInputFieldFont);
                panel3.add(nameInputField,
                                new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1,
                                                com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST,
                                                com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL,
                                                com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW,
                                                com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null,
                                                new Dimension(150, -1),
                                                null, 0, false));
                final JLabel label1 = new JLabel();
                Font label1Font = this.$$$getFont$$$(null, -1, 24, label1.getFont());
                if (label1Font != null)
                        label1.setFont(label1Font);
                label1.setHorizontalAlignment(0);
                label1.setText("What is your name?");
                panel3.add(label1,
                                new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1,
                                                com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
                                                com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL,
                                                com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
                                                com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null,
                                                null, null, 0, false));
        }

        /**
         * @noinspection ALL
         */
        private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
                if (currentFont == null)
                        return null;
                String resultName;
                if (fontName == null) {
                        resultName = currentFont.getName();
                } else {
                        Font testFont = new Font(fontName, Font.PLAIN, 10);
                        if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                                resultName = fontName;
                        } else {
                                resultName = currentFont.getName();
                        }
                }
                Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(),
                                size >= 0 ? size : currentFont.getSize());
                boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
                Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize())
                                : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
                return fontWithFallback instanceof FontUIResource ? fontWithFallback
                                : new FontUIResource(fontWithFallback);
        }

        /**
         * @noinspection ALL
         */
        public JComponent $$$getRootComponent$$$() {
                return contentPane;
        }
}
