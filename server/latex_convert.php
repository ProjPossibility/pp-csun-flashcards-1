<?php

function ConvertToLatex($input)
{
  $input = preg_replace('/squareroot\((.+?)\)/', '\sqrt{\1}', $input);
  $input = preg_replace('/fraction\((.+?),(.+?)\)/', '\frac{\1}{\2}', $input);
  $input = preg_replace('/power\((.+?),(.+?)\)/', '\1^\2', $input);
  $input = str_replace('*', '\cdot', $input);
  return $input;
}

function ConvertToText($input)
{
  $input = preg_replace('/squareroot\((.+?)\)/', ' square root of \1 ', $input);
  $input = preg_replace('/fraction\((.+?),(.+?)\)/', ' \1 divided by \2 ', $input);
  $num_power_matches = preg_match_all("/power\((.+?),(.+?)\)/", $input, $power_matches);

  for ($i = 0; $i < $num_power_matches; $i++) {
    if ($power_matches[2][$i] == 2)
      $input = str_replace($power_matches[0], ' '. $power_matches[1][$i] . ' squared ', $input);
    else if ($power_matches[2][$i] == 3)
      $input = str_replace($power_matches[0], ' '. $power_matches[1][$i] . ' cubed ', $input);
    else if (is_numeric($power_matches[2][$i]) && (int)$power_matches[2][$i] == $power_matches[2][$i]) {
      $n = abs((int)$power_matches[2][$i]);
      if ($n == 1)
        $suffix = 'st';
      else if ($n <= 20)
        $suffix = 'th';
      else if ($n % 10 == 1)
        $suffix = 'st';
      else if ($n % 10 == 2)
        $suffix = 'nd';
      else if ($n % 10 == 3)
        $suffix = 'rd';
      else
        $suffix = 'th';
      
      $input = str_replace($power_matches[0], ' '. $power_matches[1][$i] . ' to the ' . $power_matches[2][$i] . $suffix . ' power ', $input);
    } else
      $input = str_replace($power_matches[0], ' '. $power_matches[1][$i] . ' to the power of ' . $power_matches[2][$i] . ' ', $input);
  }

  $input = str_replace('+', ' plus ', $input);
  $input = str_replace('-', ' minus ', $input);
  $input = str_replace('*', ' times ', $input);
  $input = str_replace('/', ' divided by ', $input);
  $input = str_replace('=', ' equals ', $input);
  return $input;
}

function ConvertToTextFull($text) {
  preg_match_all("#\[math\](.*?)\[/math\]#si",$text,$tex_matches);

  for ($i=0; $i < count($tex_matches[0]); $i++) {
    $pos = strpos($text, $tex_matches[0][$i]);
    $english = ConvertToText($tex_matches[1][$i]);
    $text = substr_replace($text, $english,$pos,strlen($tex_matches[0][$i]));
  }

  return $text;
}
?>