package edu.dccc.conversion.ui;

import edu.dccc.conversion.config.*;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import edu.dccc.conversion.config.ConversionMap;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class ConversionGUI {

    String conversionTypesArray[] = {"Area", "Length", "Temperature"};
    String lengthTypesArray[] = {"Centimeter", "Inch", "Foot", "Kilometer", "Mile", "Millimeter", "Meter", "Yard"};
    String lengthTypesArray2[] = {"Inch", "Foot", "Kilometer", "Mile", "Millimeter", "Meter", "Yard", "Centimeter"};
    String volumeTypesArray[] = {"Cup", "Gallon", "Liter", "Milliliter", "Pint", "Quart"};
    String volumeTypesArray2[] = {"Gallon", "Liter", "Milliliter", "Pint", "Quart", "Cup"};
    String massTypesArray[] = {"Gram", "Kilogram", "MetricTon", "Milligram", "Ounce", "Pound", "Ton"};
    String massTypesArray2[] = {"Kilogram", "MetricTon", "Milligram", "Ounce", "Pound", "Ton", "Gram"};
    String areaTypesArray[] = {"Acre", "Hectare", "SquareFoot", "SquareInch", "SquareKilometer", "SquareMeter", "SquareMile", "SquareYard"};
    String areaTypesArray2[] = {"Hectare", "SquareFoot", "SquareInch", "SquareKilometer", "SquareMeter", "SquareMile", "SquareYard", "Acre"};
    String temperatureTypesArray[] = {"Celsius", "Fahrenheit"};
    String temperatureTypesArray2[] = {"Fahrenheit", "Celsius"};

    JFrame mainFrame;
    private JPanel jPanel1;
    private JSpinner conversionTypeSpinner;
    private JSpinner conversionSelectionA;
    private JSpinner conversionSelectionB;
    private JFormattedTextField textField1;
    private JFormattedTextField textField2;

    public ConversionGUI() {
        CyclingSpinnerListModel conversionTypeModel = new CyclingSpinnerListModel(conversionTypesArray);
        this.conversionTypeSpinner.setModel(conversionTypeModel);

        CyclingSpinnerListModel areaTypesModel = new CyclingSpinnerListModel(areaTypesArray);
        CyclingSpinnerListModel areaTypesModel2 = new CyclingSpinnerListModel(areaTypesArray2);
        CyclingSpinnerListModel tempTypesModel1 = new CyclingSpinnerListModel(temperatureTypesArray);
        CyclingSpinnerListModel tempTypesModel2 = new CyclingSpinnerListModel(temperatureTypesArray2);
        CyclingSpinnerListModel lengthTypeModel = new CyclingSpinnerListModel(lengthTypesArray);
        CyclingSpinnerListModel lengthTypeModel2 = new CyclingSpinnerListModel(lengthTypesArray2);

        CyclingSpinnerListModel volumeTypeModel = new CyclingSpinnerListModel(volumeTypesArray);
        CyclingSpinnerListModel volumeTypeModel2 = new CyclingSpinnerListModel(volumeTypesArray2);

        ConversionMap conversionMap = new ConversionMap();
        try {
            conversionMap.initializeMaps("resources/ConversionsLong.csv");
        } catch (IOException io) {
            System.out.println("Problem reading config files: " + io.getMessage());
        }
        conversionTypeSpinner.setValue("Length");
        conversionSelectionA.setModel(lengthTypeModel);
        conversionSelectionB.setModel(lengthTypeModel2);
        textField1.setText("1");
        try {
            double answer = conversionMap.convert(conversionSelectionA.getValue().toString(), conversionSelectionB.getValue().toString(), Double.parseDouble(textField1.getText()));
            textField2.setText(String.format("%.5f", answer));
        } catch (ConversionMap.ConversionLookupException ex) {
            String errorMessage = "Conversion Lookup Exception: " + conversionSelectionB.getValue().toString() + "-" + conversionSelectionA.getValue().toString();
            showErrorMessage(errorMessage);
        }
        conversionTypeSpinner.addChangeListener(new
                                                        ChangeListener() {
                                                            @Override
                                                            public void stateChanged(ChangeEvent e) {
                                                                if (conversionTypeSpinner.getValue().equals("Length")) {
                                                                    conversionSelectionA.setModel(lengthTypeModel);
                                                                    conversionSelectionB.setModel(lengthTypeModel2);
                                                                } else if (conversionTypeSpinner.getValue().equals("Area")) {
                                                                    conversionSelectionA.setModel(areaTypesModel);
                                                                    conversionSelectionB.setModel(areaTypesModel2);
                                                                } else if (conversionTypeSpinner.getValue().equals("Temperature")) {
                                                                    conversionSelectionA.setModel(tempTypesModel1);
                                                                    conversionSelectionB.setModel(tempTypesModel2);
                                                                }
                                                                else if (conversionTypeSpinner.getValue().equals("Volume")) {
                                                                  conversionSelectionA.setModel(volumeTypeModel);
                                                                   conversionSelectionB.setModel(volumeTypeModel2);
                                                              }
                                                                //  ToDO: Add else ifs for Mass and Volume
                                                            }
                                                        });

        conversionSelectionA.addChangeListener(new
                                                       ChangeListener() {
                                                           @Override
                                                           public void stateChanged(ChangeEvent e) {
                                                               SpinnerModel model = conversionSelectionA.getModel();
                                                               String field1Text = textField1.getText();
                                                               double num = 0.0;
                                                               double answer = 0.0;
                                                               if (!field1Text.equals("")) {
                                                                   num = Double.parseDouble(textField1.getText());
                                                               }
                                                               try {
                                                                   answer = conversionMap.convert(conversionSelectionA.getValue().toString(), conversionSelectionB.getValue().toString(), num);
                                                                   textField2.setText(String.format("%.5f", answer));
                                                               } catch (ConversionMap.ConversionLookupException ex) {
                                                                   String errorMessage = "Conversion Lookup Exception: " + conversionSelectionA.getValue().toString() + "-" + conversionSelectionB.getValue().toString();
                                                                   showErrorMessage(errorMessage);
                                                               }

                                                           }
                                                       });


        conversionSelectionB.addChangeListener(new
                                                       ChangeListener() {
                                                           @Override
                                                           public void stateChanged(ChangeEvent e) {
                                                               String text = textField1.getText();
                                                               double num = 0.0;
                                                               if (!text.equals(""))
                                                                   num = Double.parseDouble(textField1.getText());
                                                               try {
                                                                   double answer = conversionMap.convert(conversionSelectionA.getValue().toString(), conversionSelectionB.getValue().toString(), num);
                                                                   textField2.setText(String.format("%.5f", answer));
                                                               } catch (ConversionMap.ConversionLookupException ex) {
                                                                   String errorMessage = "Conversion Lookup Exception: " + conversionSelectionA.getValue().toString() + "-" + conversionSelectionB.getValue().toString();
                                                               }
                                                           }
                                                       });


        textField2.addKeyListener(new
                                          KeyAdapter() {
                                              public void keyReleased(KeyEvent ke) {
                                                  if (!(ke.getKeyChar() == 27 || ke.getKeyChar() == 65535))//this section will execute only when user is editing the JTextField
                                                  {
                                                      String text = textField2.getText();
                                                      double num = 0.0;
                                                      try {
                                                      if (!text.equals(""))
                                                          num = Double.parseDouble(textField2.getText());

                                                          double answer = conversionMap.convert(conversionSelectionB.getValue().toString(), conversionSelectionA.getValue().toString(), num);
                                                          textField1.setText(String.format("%.5f", answer));
                                                      } catch (ConversionMap.ConversionLookupException ex) {
                                                          String errorMessage = "Conversion Lookup Exception: " + conversionSelectionB.getValue().toString() + "-" + conversionSelectionA.getValue().toString();
                                                          showErrorMessage(errorMessage);
                                                      }
                                                      catch (NumberFormatException e3) {
                                                         showErrorMessage("NumberFormatException: " + e3.getMessage());
                                                       }
                                                  }
                                              }
                                          });

        textField1.addKeyListener(new
                                          KeyAdapter() {
                                              public void keyReleased(KeyEvent ke) {
                                                  if (!(ke.getKeyChar() == 27 || ke.getKeyChar() == 65535))//this section will execute only when user is editing the JTextField
                                                  {
                                                      try {
                                                      String text = textField1.getText();
                                                      double num = 0.0;

                                                      if (!text.equals(""))
                                                          num = Double.parseDouble(textField1.getText());

                                                          double answer = conversionMap.convert(conversionSelectionA.getValue().toString(), conversionSelectionB.getValue().toString(), num);
                                                          textField2.setText(String.format("%.3f", answer));
                                                      } catch (ConversionMap.ConversionLookupException ex) {
                                                          String errorMessage = "Conversion Lookup Exception: " + conversionSelectionA.getValue().toString() + "-" + conversionSelectionB.getValue().toString();
                                                          showErrorMessage(errorMessage);
                                                      }
                                                      catch (NumberFormatException e3) {
                                                          showErrorMessage("NumberFormatException: " + e3.getMessage());
                                                      }
                                                  }
                                              }
                                          });
    }

    public static void main(String[] args) {
        ConversionGUI gui = new ConversionGUI();
        JFrame frame = new JFrame("Unit Conversion Tool");
        gui.mainFrame = frame;
        frame.setBounds(300, 300, 400, 150);
        frame.setContentPane(new ConversionGUI().jPanel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(mainFrame, message, "Unit Conversion Tool Error",
                JOptionPane.ERROR_MESSAGE);
    }

    ///////////////////   Do Not Modify code below this line.  Generated by IntelliJ IDEA GUI Designer

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
        jPanel1 = new JPanel();
        jPanel1.setLayout(new GridLayoutManager(5, 6, new Insets(0, 0, 0, 0), -1, -1));
        final JLabel label1 = new JLabel();
        label1.setHorizontalAlignment(0);
        label1.setHorizontalTextPosition(0);
        label1.setText("Unit Conversion Tool");
        jPanel1.add(label1, new GridConstraints(0, 1, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(111, 23), null, 0, false));
        final Spacer spacer1 = new Spacer();
        jPanel1.add(spacer1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        conversionTypeSpinner = new JSpinner();
        conversionTypeSpinner.setToolTipText("Select Conversion Type");
        jPanel1.add(conversionTypeSpinner, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textField1 = new JFormattedTextField();
        jPanel1.add(textField1, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("=");
        jPanel1.add(label2, new GridConstraints(2, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textField2 = new JFormattedTextField();
        jPanel1.add(textField2, new GridConstraints(2, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final Spacer spacer2 = new Spacer();
        jPanel1.add(spacer2, new GridConstraints(2, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        conversionSelectionA = new JSpinner();
        jPanel1.add(conversionSelectionA, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        conversionSelectionB = new JSpinner();
        jPanel1.add(conversionSelectionB, new GridConstraints(3, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        jPanel1.add(spacer3, new GridConstraints(4, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return jPanel1;
    }

    class CyclingSpinnerListModel extends SpinnerListModel {
        Object firstValue, lastValue;
        SpinnerModel linkedModel = null;

        public CyclingSpinnerListModel(Object[] values) {
            super(values);
            firstValue = values[0];
            lastValue = values[values.length - 1];
        }

        public void setLinkedModel(SpinnerModel linkedModel) {
            this.linkedModel = linkedModel;
        }

        public Object getNextValue() {
            Object value = super.getNextValue();
            if (value == null) {
                value = firstValue;
                if (linkedModel != null) {
                    linkedModel.setValue(linkedModel.getNextValue());
                }
            }
            return value;
        }

        public Object getPreviousValue() {
            Object value = super.getPreviousValue();
            if (value == null) {
                value = lastValue;
                if (linkedModel != null) {
                    linkedModel.setValue(linkedModel.getPreviousValue());
                }
            }
            return value;
        }
    }


}
